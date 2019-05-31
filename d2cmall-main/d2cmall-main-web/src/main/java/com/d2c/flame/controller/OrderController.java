package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.*;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.handle.PromotionCondition;
import com.d2c.order.model.*;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.model.CouponDef.Association;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDefRelation.CouponRelationType;
import com.d2c.order.model.Order.OrderTerminal;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.Store.BusTypeEnum;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.model.BargainPriceDO.BargainStatus;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.OrderTxService;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BargainPromotionService;
import com.d2c.product.service.ProductCombService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.RandomUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductCombService productCombService;
    @Autowired
    private PromotionRunService promotionRunService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Reference
    private OrderTxService orderTxService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SettingService settingService;
    @Autowired
    private BargainPriceService bargainPriceService;
    @Autowired
    private BargainPromotionService bargainPromotionService;

    /**
     * 立即购买
     *
     * @param model
     * @param skuId
     * @param num
     * @return
     */
    @RequestMapping(value = "/buynow", method = {RequestMethod.POST, RequestMethod.GET})
    public String buyNow(ModelMap model, @RequestParam(required = true) Long[] skuId,
                         @RequestParam(required = true) Integer[] num) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        boolean tick = (memberInfo.getDistributorId() == null);
        for (int i = 0; i < skuId.length; i++) {
            ProductSku productSku = productSkuService.findById(skuId[i]);
            if (productSku == null) {
                continue;
            }
            Product product = productService.findById(productSku.getProductId());
            if (product == null) {
                continue;
            }
            tick = tick && (product.getStatus() != 5 && product.getStatus() != -1);
            this.checkQuantityAndStatus(productSku, product, num[i], OrderType.ordinary);
            // 产生购物车明细
            CartItemDto cartItemDto = this.createCartItemDto(product, productSku, memberInfo, num[i]);
            PromotionCondition pc = new PromotionCondition(cartItemDto, product, productSku, true);
            promotionConditions.add(pc);
            // 产生订单明细
            OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
            orderItems.add(orderItemDto);
        }
        // 初始化活动
        PromotionCalculateResult calResult = promotionRunService.getPromotionsByOrder(memberInfo.getDistributorId(),
                null, promotionConditions);
        this.initPromotion(orderDto, orderItems, calResult);
        model.put("order", orderDto);
        model.put("orderItems", orderItems);
        // 收货地址，优惠券
        this.getOtherInfo(orderDto, model, tick);
        return "order/confirm_order";
    }

    /**
     * 砍价商品立即购买
     *
     * @param model
     * @param skuId
     * @param num
     * @param bargainId
     * @return
     */
    @RequestMapping(value = "/bargain/buynow", method = {RequestMethod.POST, RequestMethod.GET})
    public String buyNowBargain(ModelMap model, Long skuId, Integer num, String bargainId) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (num == null) {
            num = 1;
        }
        if (skuId == null) {
            throw new BusinessException("没有选择购买的商品，请刷新页面后，重新购买！");
        }
        ProductSku productSku = productSkuService.findById(skuId);
        if (productSku == null) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        Product product = productService.findById(productSku.getProductId());
        if (product == null || product.getMark() <= 0) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                || product.getSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("该商品暂仅支持app和小程序购买！");
        }
        this.checkQuantityAndStatus(productSku, product, num, OrderType.bargain);
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        // 产生购物车明细
        CartItemDto cartItemDto = this.createCartItemDto(product, productSku, memberInfo, num);
        // 砍价活动处理
        if (StringUtil.isNotBlank(bargainId)) {
            this.initBargainPrice(bargainId, cartItemDto, memberInfo.getId());
        }
        PromotionCondition pc = new PromotionCondition();
        BeanUtils.copyProperties(cartItemDto, pc, "goodPromotionId", "orderPromotionId", "flashPromotionId");
        promotionConditions.add(pc);
        // 产生订单明细
        OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
        orderItems.add(orderItemDto);
        orderDto.setOrderItems(orderItems);
        // 初始化活动
        PromotionCalculateResult calResult = promotionRunService.getPromotionsByOrder(memberInfo.getDistributorId(),
                null, promotionConditions);
        this.initPromotion(orderDto, orderItems, calResult);
        model.put("order", orderDto);
        model.put("orderItems", orderItems);
        model.put("bargainId", bargainId);
        // 收货地址，优惠券
        this.getOtherInfo(orderDto, model, false);
        return "order/confirm_bargain";
    }

    /**
     * 组合商品立即购买
     *
     * @param model
     * @param productCombId
     * @param num
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/comb/buynow", method = {RequestMethod.POST, RequestMethod.GET})
    public String buyNowComb(ModelMap model, @RequestParam(required = true) Long productCombId,
                             @RequestParam(required = true) Integer num, @RequestParam(required = true) Long skuId[]) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (productCombId == null || productCombId <= 0) {
            throw new BusinessException("组合商品不存在，下单不成功！");
        }
        ProductCombDto productComb = productCombService.findDtoById(productCombId);
        if (productComb == null || productComb.getMark() <= 0) {
            throw new BusinessException("组合商品不存在，下单不成功！");
        }
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        if (skuId == null || skuId.length != productComb.getProducts().size()) {
            throw new BusinessException("组合商品，选择不全，下单不成功！");
        }
        List<Long> procombs = new ArrayList<>();
        productComb.getProducts().forEach(item -> procombs.add(item.getId()));
        boolean tick = (memberInfo.getDistributorId() == null);
        for (int i = 0; i < skuId.length; i++) {
            ProductSku productSku = productSkuService.findById(skuId[i]);
            if (productSku == null) {
                throw new BusinessException("组合商品，选择不全，下单不成功！");
            }
            Product product = productService.findById(productSku.getProductId());
            tick = tick && (product.getStatus() != 5 && product.getStatus() != -1);
            this.checkQuantityAndStatus(productSku, product, num, OrderType.ordinary);
            if (!procombs.contains(product.getId())) {
                throw new BusinessException("商品" + product.getName() + "不在组合商品序列中，请重新选择下单！");
            }
            // 产生购物车明细
            CartItemDto cartItemDto = this.createCartItemDto(product, productSku, memberInfo, num);
            PromotionCondition pc = new PromotionCondition();
            BeanUtils.copyProperties(cartItemDto, pc);
            pc.setProductCombId(productCombId);
            promotionConditions.add(pc);
            // 产生订单明细
            OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
            orderItemDto.setProductCombId(productCombId);
            orderItems.add(orderItemDto);
        }
        // 初始化活动
        PromotionCalculateResult calResult = promotionRunService.getProductCombPromotion(promotionConditions);
        this.initPromotion(orderDto, orderItems, calResult);
        model.put("order", orderDto);
        model.put("orderItems", orderItems);
        // 收货地址，优惠券
        this.getOtherInfo(orderDto, model, tick);
        // 运费
        model.put("shipFee", orderDto.getShippingRates());
        return "order/confirm_order";
    }

    /**
     * 确认订单
     *
     * @param model
     * @param cartItemId
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmOrder(ModelMap model, @RequestParam(required = true) Long[] cartItemId) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (cartItemId == null || cartItemId.length <= 0) {
            return "redirect:/cart/list";
        }
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        Cart cart = cartService.findCart(memberInfo.getId());
        List<Long> skuIds = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        for (int i = 0; i < cartItemId.length; i++) {
            CartItemDto cartItem = cart.getItemMapById().get(cartItemId[i]);
            if (cartItem == null) {
                continue;
            }
            skuIds.add(cartItem.getProductSkuId());
            productIds.add(cartItem.getProductId());
        }
        Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuIds.toArray(new Long[]{}));
        Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
        boolean tick = (memberInfo.getDistributorId() == null);
        for (int i = 0; i < cartItemId.length; i++) {
            // 获取购物车明细
            CartItemDto cartItemDto = cart.getItemMapById().get(cartItemId[i]);
            if (cartItemDto == null) {
                continue;
            }
            ProductSku productSku = skuMap.get(cartItemDto.getProductSkuId());
            Product product = productMap.get(cartItemDto.getProductId());
            if (productSku == null || product == null) {
                continue;
            }
            tick = tick && (product.getStatus() != 5 && product.getStatus() != -1);
            this.checkQuantityAndStatus(productSku, product, cartItemDto.getQuantity(), OrderType.ordinary);
            PromotionCondition pc = new PromotionCondition(cartItemDto, product, productSku, true);
            promotionConditions.add(pc);
            // 产生订单明细
            OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
            orderItems.add(orderItemDto);
        }
        // 初始化活动
        PromotionCalculateResult calResult = promotionRunService.getPromotionsByOrder(memberInfo.getDistributorId(),
                null, promotionConditions);
        this.initPromotion(orderDto, orderItems, calResult);
        model.put("order", orderDto);
        model.put("orderItems", orderItems);
        // 收货地址，优惠券
        this.getOtherInfo(orderDto, model, tick);
        return "order/confirm_order";
    }

    /**
     * 收货地址，优惠券
     *
     * @param orderDto
     * @param model
     * @param coupon
     */
    protected void getOtherInfo(OrderDto orderDto, ModelMap model, boolean coupon) {
        // 收货地址
        PageResult<AddressDto> addresses = addressService.findByMemberInfoId(orderDto.getMemberId(),
                new PageModel(1, 100), null);
        model.put("addresses", addresses.getList());
        // 优惠券
        PageResult<Coupon> couponPager = couponQueryService.findMyUnusedCoupons(orderDto.getMemberId(),
                orderDto.getTotalAmount());
        Set<Long> defineIds = new HashSet<>();
        couponPager.getList().forEach(item -> defineIds.add(item.getDefineId()));
        List<Long> ableIds = new ArrayList<>();
        List<OrderItemDto> initItems = this.checkValidItem(orderDto);
        for (Long defineId : defineIds) {
            CouponDef cd = couponDefQueryService.findById(defineId);
            if (orderDto.getTotalAmount().intValue() < cd.getNeedAmount()
                    || cd.getEnableDate().compareTo(new Date()) > 0) {
                continue;
            }
            List<OrderItemDto> validItems = new ArrayList<>();
            if (this.checkValidCoupon(orderDto, cd, initItems, validItems)) {
                ableIds.add(defineId);
            }
        }
        List<Coupon> ableCoupons = new ArrayList<>();
        List<Coupon> disableCoupons = new ArrayList<>();
        for (Coupon item : couponPager.getList()) {
            if (ableIds.contains(item.getDefineId())) {
                ableCoupons.add(item);
            } else {
                disableCoupons.add(item);
            }
        }
        if (coupon) {
            model.put("ableCoupons", ableCoupons);
            model.put("disableCoupons", disableCoupons);
        } else {
            model.put("ableCoupons", new ArrayList<Coupon>());
            disableCoupons.addAll(ableCoupons);
            model.put("disableCoupons", disableCoupons);
        }
    }

    /**
     * 砍价优惠
     *
     * @param bargainId
     * @param cartItemDto
     * @param memberId
     * @return
     */
    protected boolean initBargainPrice(String bargainId, CartItemDto cartItemDto, Long memberId) {
        BargainPriceDO bargainPrice = bargainPriceService.findById(bargainId);
        // 只有自己发起的砍价才能购买
        if (!bargainPrice.getMemberId().equals(memberId)) {
            throw new BusinessException("该砍价不是您自己的，无法购买！");
        }
        // 每发起一笔砍价只能购买一次
        if (bargainPrice.getStatus().equals(BargainStatus.ORDERED.name())
                || bargainPrice.getStatus().equals(BargainStatus.BUYED.name())) {
            throw new BusinessException("该砍价已经被您购买过啦！");
        }
        // 活动结束后的24小时还能购买
        BargainPromotion bargainpromotion = bargainPromotionService.findById(bargainPrice.getBargainId());
        bargainpromotion.setEndDate(new Date(bargainpromotion.getEndDate().getTime() + 24 * 60 * 60 * 1000));
        if (bargainpromotion == null || bargainpromotion.isOver()) {
            throw new BusinessException("该砍价商品已超出购买期限！");
        }
        // 活动期间只能购买3次砍价商品
        BargainPriceSearcher searcher = new BargainPriceSearcher();
        searcher.setStartDate(DateUtil.str2day("2018-12-05"));
        searcher.setEndDate(DateUtil.str2day("2018-12-13"));
        searcher.setMemberId(memberId);
        searcher.setStatusArray(new String[]{BargainStatus.ORDERED.name(), BargainStatus.BUYED.name()});
        int count = bargainPriceService.countBySearcher(searcher);
        if (count >= 3) {
            throw new BusinessException("活动期间您最多只能购买3次砍价商品！");
        }
        cartItemDto.setPrice(new BigDecimal(bargainPrice.getPrice()));
        return true;
    }

    /**
     * 优惠券条件
     *
     * @param skuIds
     * @param productIds
     * @param designerIds
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    public String checkCoupon(ModelMap model, Long[] skuIds, Long[] productIds, Long[] designerIds, String coupons) {
        MemberInfo member = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Coupon coupon = couponQueryService.findByCode(coupons, member.getId());
        if (coupon == null || !coupon.getStatus().equals(CouponStatus.CLAIMED.name())) {
            throw new BusinessException("优惠券不存在！");
        }
        CouponDef couponDef = couponDefQueryService.findById(coupon.getDefineId());
        Long[] targetIds = null;
        String relationType = null;
        if (couponDef.getCheckAssociation().equals(Association.ALL.name())) {
            result.put("skuIds", skuIds);
            return "";
        } else if (couponDef.getCheckAssociation().equals(Association.PRODUCT.name())) {
            relationType = CouponRelationType.PRODUCT.name();
            targetIds = productIds;
        } else if (couponDef.getCheckAssociation().equals(Association.DESIGNER.name())) {
            relationType = CouponRelationType.DESIGNER.name();
            targetIds = designerIds;
        }
        List<CouponDefRelation> relations = couponDefRelationService.findByCidAndTids(Arrays.asList(targetIds),
                couponDef.getId(), relationType);
        Map<Long, Long> relMap = new HashMap<>();
        for (CouponDefRelation relation : relations) {
            relMap.put(relation.getTargetId(), relation.getCouponDefId());
        }
        List<Long> validItem = new ArrayList<>();
        for (int i = 0; i < targetIds.length; i++) {
            if (!couponDef.getExclude().booleanValue()) {
                if (relMap.containsKey(targetIds[i]))
                    validItem.add(skuIds[i]);
            } else {
                if (!relMap.containsKey(targetIds[i]))
                    validItem.add(skuIds[i]);
            }
        }
        result.put("skuIds", validItem);
        return "";
    }

    /**
     * 创建订单
     *
     * @param skuId
     * @param quantity
     * @param productCombId
     * @param addressId
     * @param address
     * @param coupons
     * @param memo
     * @param drawee
     * @param redPacket
     * @param model
     * @param tempId
     * @param bargainId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(Long[] skuId, Integer[] quantity, Long[] productCombId, Long addressId,
                              AddressDto address, String coupons, String memo, String drawee, Integer redPacket, ModelMap model,
                              String tempId, String bargainId, Long collageId, BigDecimal longitude, BigDecimal latitude)
            throws Exception {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        model.put("cod", 0);
        // 防止重复提交的锁
        final String memberKey = "create_order_member_info_" + memberInfo.getId();
        try {
            Boolean exist = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(memberKey.getBytes());
                }
            });
            if (exist != null && exist.booleanValue()) {
                throw new BusinessException("不能重复提交订单！");
            } else {
                redisTemplate.execute(new RedisCallback<Long>() {
                    @Override
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.set(memberKey.getBytes(), String.valueOf(memberInfo.getId()).getBytes());
                        return 1L;
                    }
                });
            }
            // 收货地址
            if (addressId == null || addressId <= 0) {
                if (address != null) {
                    address.setMemberId(memberInfo.getId());
                    address = addressService.insert(address);
                } else {
                    throw new BusinessException("请填写收货地址！");
                }
            } else {
                address = addressService.findById(addressId);
            }
            if (address == null || !memberInfo.getId().equals(address.getMemberId())) {
                throw new BusinessException("收货地址异常！");
            }
            if (skuId == null || skuId[0] == null) {
                throw new BusinessException("购买商品已经售罄，请选购其他商品！");
            }
            OrderDto orderDto = null;
            if (!StringUtils.isEmpty(tempId)) {
                orderDto = orderService.findByTempId(tempId);
            }
            // 防止页面重复刷新
            if (orderDto != null) {
                if (!orderDto.isWaitPay()) {
                    throw new BusinessException("订单已支付或关闭，支付不成功！");
                } else {
                    orderDto = orderService.updateTempOrder(orderDto.getId(), address.getId(), coupons, memo,
                            memberInfo.getId());
                }
            } else {
                // 产生订单
                orderDto = new OrderDto(memberInfo);
                orderDto.setReciverInfo(address);
                orderDto.setMemo(memo);
                orderDto.setDevice(isNormalDevice() ? DeviceTypeEnum.PC.name() : DeviceTypeEnum.MOBILE.name());
                orderDto.setAppVersion(VERSION);
                orderDto.setDrawee(drawee);
                orderDto.setTempId(tempId);
                orderDto.setLongitude(longitude);
                orderDto.setLatitude(latitude);
                List<Long> productIds = new ArrayList<>();
                Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuId);
                Iterator<Entry<Long, ProductSku>> iterator = skuMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<Long, ProductSku> entry = iterator.next();
                    ProductSku sku = entry.getValue();
                    if (sku == null) {
                        continue;
                    }
                    productIds.add(sku.getProductId());
                }
                Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
                boolean tick = (memberInfo.getDistributorId() == null);
                boolean qualified = false;
                OrderType orderType = OrderType.ordinary;
                List<IPromotionInterface> promotionConditions = new ArrayList<>();
                List<OrderItemDto> orderItems = new ArrayList<>();
                for (int i = 0; i < skuId.length; i++) {
                    ProductSku productSku = skuMap.get(skuId[i]);
                    if (productSku == null) {
                        continue;
                    }
                    Product product = productMap.get(productSku.getProductId());
                    if (product == null) {
                        continue;
                    }
                    Boolean promotion = true;
                    // 秒杀商品
                    if (5 == product.getStatus()) {
                        tick = tick & false;
                        orderType = OrderType.seckill;
                        orderDto.setType(orderType.name());
                    }
                    // 礼包商品
                    if (-1 == product.getStatus()) {
                        tick = tick & false;
                        qualified = qualified | true;
                    }
                    // 产生购物车明细
                    CartItemDto cartItemDto = this.createCartItemDto(product, productSku, memberInfo, quantity[i]);
                    // 拼团活动处理
                    if (collageId != null) {
                        orderType = OrderType.collage;
                        orderDto.setType(orderType.name());
                        orderDto.setCollageId(collageId);
                        promotion = false;
                    }
                    // 砍价活动处理
                    if (StringUtil.isNotBlank(bargainId)) {
                        this.initBargainPrice(bargainId, cartItemDto, memberInfo.getId());
                        orderType = OrderType.bargain;
                        orderDto.setType(orderType.name());
                        orderDto.setBargainId(bargainId);
                        promotion = false;
                    }
                    this.checkQuantityAndStatus(productSku, product, cartItemDto.getQuantity(), orderType);
                    PromotionCondition pc = new PromotionCondition(cartItemDto, product, productSku, promotion);
                    promotionConditions.add(pc);
                    // 产生订单明细
                    OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
                    if (isComb(productCombId)) {
                        pc.setProductCombId(productCombId[0]);
                        orderItemDto.setProductCombId(productCombId[0]);
                    }
                    orderItems.add(orderItemDto);
                }
                // 初始化活动
                PromotionCalculateResult calResult = null;
                if (isComb(productCombId)) {
                    // 组合商品
                    calResult = promotionRunService.getProductCombPromotion(promotionConditions);
                } else {
                    // 一般订单
                    calResult = promotionRunService.getPromotionsByOrder(memberInfo.getDistributorId(), null,
                            promotionConditions);
                }
                // 设置活动
                this.initPromotion(orderDto, orderItems, calResult);
                if (!orderDto.getType().equals(OrderType.distribution.name())) {
                    // 设置优惠券
                    this.initCoupon(coupons, orderDto, tick);
                    // 设置红包
                    this.initRedPacks(redPacket, orderDto, memberInfo, tick);
                    // 设置分销规则
                    orderDto = orderService.doInitPartner(memberInfo, orderDto, processCookie(orderDto), 2, qualified);
                } else {
                    // 设置D+店信息
                    this.initDPlus(orderDto, memberInfo);
                }
                // 此处创建订单
                orderDto = orderTxService.doCreateOrder(orderDto, coupons, redPacket);
                // 清空购物车
                Cart cart = cartService.findCart(memberInfo.getId());
                List<Long> delCartItemIds = structCartItemIds(cart, skuId);
                if (delCartItemIds != null && delCartItemIds.size() > 0) {
                    cartService.deleteCartItems(delCartItemIds, memberInfo.getId());
                }
                try {
                    // 订单创建提醒
                    String value = "86400";
                    if (orderDto.getCrossBorder() == 1) {
                        Setting setting = settingService.findByCode(Setting.CROSSCLOSECODE);
                        value = Setting.defaultValue(setting, "1200").toString();
                    } else if (orderDto.getType().equals(Order.OrderType.seckill.toString())
                            || orderDto.getType().equals(Order.OrderType.bargain.toString())) {
                        Setting setting = settingService.findByCode(Setting.SECKILLCLOSECODE);
                        value = Setting.defaultValue(setting, "3600").toString();
                    } else {
                        Setting setting = settingService.findByCode(Setting.ORDERCLOSECODE);
                        value = Setting.defaultValue(setting, "86400").toString();
                    }
                    this.orderRemindMQ(orderDto, value);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (orderDto.getOrderStatus() == 1) {
                // 冻结砍价单
                if (!StringUtils.isEmpty(bargainId)) {
                    bargainPriceService.updateBargainForPay(bargainId);
                }
                // 钱包账户
                Account account = accountService.findByMemberId(memberInfo.getId());
                model.put("account", account);
                result.put("order", orderDto);
                result.put("params", this.getPayParams(orderDto));
                return "pay/payment";
            } else {
                throw new BusinessException("订单已支付或关闭，支付不成功！");
            }
        } finally {
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(memberKey.getBytes());
                    return 1L;
                }
            });
        }
    }

    private Long processCookie(OrderDto orderDto) {
        try {
            Cookie[] cookies = getRequest().getCookies();
            if (cookies != null && cookies.length >= 1) {
                for (int j = 0; j < cookies.length; j++) {
                    if (cookies[j].getName().equalsIgnoreCase("d2cCookie")) {
                        String channelStr = cookies[j].getValue().split(",")[0];
                        String channel = channelStr.split(":")[1];
                        String infoStr = cookies[j].getValue().split(",")[1];
                        String info = infoStr.split(":")[1];
                        if (channel.equalsIgnoreCase(OrderTerminal.Partner.name())) {
                            return Long.valueOf(info);
                        } else if (channel.equalsIgnoreCase(OrderTerminal.Weibo.name())) {
                            orderDto.setTerminal(OrderTerminal.Weibo.name());
                            orderDto.setTerminalId(orderDto.getMemberMobile());
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void orderRemindMQ(OrderDto orderDto, String seconds) {
        Long delayTime = DateUtil.add(new Date(), Calendar.SECOND, Integer.parseInt(seconds)).getTime()
                - System.currentTimeMillis() - 30 * 60 * 1000;
        if (delayTime > 60 * 1000) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderDto.getId());
            map.put("minutes", 30);
            MqEnum.ORDER_REMIND.send(map, delayTime / 1000);
        }
    }

    /**
     * 检验商品的状态和明细
     *
     * @param productSku
     * @param product
     * @param quantity
     */
    private void checkQuantityAndStatus(ProductSku productSku, Product product, Integer quantity, OrderType orderType) {
        // 当前商品下架或者删除, 不能下订单
        if (product == null || product.getMark() <= 0) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        // 当前商品下架或者删除, 不能下订单
        if (productSku == null || productSku.getStatus() <= 0) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        // 当前商品数量异常
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("商品数量异常，购买不成功！");
        }
        // 当前没有销售的库存了, 不能下订单
        if (productSku.getAvailableStore() < quantity) {
            throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue() + "颜色："
                    + productSku.getColorValue() + "，商品库存不足" + quantity + "件，下次早点来哦！");
        }
        // 当前没有销售的库存了, 不能下订单
        if (orderType != null && (orderType.equals(OrderType.bargain) || orderType.equals(OrderType.collage))) {
            if (productSku.getAvailableFlashStore() < quantity) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，商品库存不足" + quantity + "件，下次早点来哦！");
            }
        }
        if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                || product.getSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("该商品暂仅支持app和小程序购买！");
        }
    }

    /**
     * 创建购物车
     *
     * @param product
     * @param productSku
     * @param member
     * @param quantity
     * @return
     */
    protected CartItemDto createCartItemDto(Product product, ProductSku productSku, MemberInfo member, int quantity) {
        CartItem cartItem = new CartItem(product, productSku, member);
        cartItem.setId(0L);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(productSku.getPrice());
        CartItemDto cartItemDto = new CartItemDto();
        BeanUtils.copyProperties(cartItem, cartItemDto);
        cartItemDto.initCondition(product.getGoodPromotionId(), product.getOrderPromotionId(),
                product.getFlashPromotionId(), productSku.getaPrice(), productSku.getFlashPrice(),
                productSku.getAvailableFlashStore());
        return cartItemDto;
    }

    /**
     * 创建订单明细
     *
     * @param cartItemDto
     * @param product
     * @param productSku
     * @param member
     * @return
     */
    protected OrderItemDto createOrderItemDto(CartItemDto cartItemDto, Product product, ProductSku productSku,
                                              MemberInfo member) {
        OrderItemDto orderItemDto = new OrderItemDto(product, cartItemDto);
        orderItemDto.setProductSku(productSku);
        orderItemDto.setDeliveryBarCode(productSku.getSn());
        orderItemDto.setBuyerMemberId(member.getId());
        orderItemDto.setBuyerMemberName(member.getLoginCode());
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        BigDecimal partnerRatio = product.getSecondRatio().multiply(product.getGrossRatio());
        String ratioValue = Setting.defaultValue(ratio, new Integer(1)).toString();
        partnerRatio = partnerRatio.multiply(new BigDecimal(ratioValue)).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderItemDto.setPartnerRatio(partnerRatio);
        // 第三方货币价
        orderItemDto.setThirdCcyPrice(product.getKaolaPrice());
        // if (ProductSource.MILAN.name().equals(product.getSource())) {
        // Setting hkdExRate = settingService.findByCode(Setting.HKDEXRATE);
        // if (hkdExRate != null && StringUtil.isNumber(hkdExRate.getValue())) {
        // orderItemDto.setThirdCcyPrice(product.getKaolaPrice().divide(new
        // BigDecimal(hkdExRate.getValue()), 2,
        // BigDecimal.ROUND_HALF_UP));
        // }
        // } else {
        // orderItemDto.setThirdCcyPrice(product.getKaolaPrice());
        // }
        return orderItemDto;
    }

    /**
     * 是否是组合商品
     *
     * @param productCombId
     * @return
     */
    private boolean isComb(Long[] productCombId) {
        return productCombId != null && productCombId.length > 0 && productCombId[0] > 0;
    }

    /**
     * 活动优惠
     *
     * @param orderItems
     * @param calResult
     */
    protected void initPromotion(OrderDto orderDto, List<OrderItemDto> orderItems, PromotionCalculateResult calResult) {
        orderDto.initPromotion(calResult, orderItems);
        for (OrderItemDto orderItem : orderItems) {
            orderItem.setType(calResult.getType());
            for (PromotionCalculateItem pi : calResult.getItems()) {
                Long key = orderItem.getCartItemId();
                if (key == null || key <= 0) {
                    key = orderItem.getProductSkuId();
                }
                if (key.equals(pi.getKey())) {
                    orderItem = orderItem.initPromotion(pi);
                }
            }
        }
        orderDto.setOrderItems(orderItems);
        this.initShippingRates(orderDto, orderItems, calResult);
    }

    /**
     * 计算运费
     *
     * @param orderItems
     * @param calResult
     */
    private void initShippingRates(OrderDto orderDto, List<OrderItemDto> orderItems,
                                   PromotionCalculateResult calResult) {
        // 经销商订单运费另算
        if (calResult.getType().equals(OrderType.distribution.name())) {
            orderDto.setType(calResult.getType());
            orderDto.setShippingRates(calResult.getShipFee());
        } else {
            // 普通订单运费10元满299包邮, 草莓订单运费60元满210包邮, 考拉订单运费由考拉接口
            BigDecimal d2cAmount = new BigDecimal(0);
            BigDecimal caomeiAmount = new BigDecimal(0);
            BigDecimal milanAmount = new BigDecimal(0);
            boolean shipping = false;
            for (OrderItemDto orderItem : orderItems) {
                if (ProductSource.D2CMALL.name().equals(orderItem.getProductSource())) {
                    d2cAmount = d2cAmount.add(orderItem.getActualAmount());
                }
                if (ProductSource.CAOMEI.name().equals(orderItem.getProductSource())) {
                    caomeiAmount = caomeiAmount.add(orderItem.getActualAmount());
                }
                if (ProductSource.MILAN.name().equals(orderItem.getProductSource())) {
                    milanAmount = milanAmount.add(orderItem.getActualAmount());
                }
                if (orderItem.getShipping() == 1) {
                    shipping = true;
                }
            }
            BigDecimal d2cShippingRates = PromotionCalculateResult.shippingRates(new BigDecimal(299),
                    new BigDecimal(10), d2cAmount);
            if (shipping) {
                // D2C的商品包邮
                d2cShippingRates = new BigDecimal(0);
            }
            BigDecimal caomeiShippingRates = PromotionCalculateResult.shippingRates(new BigDecimal(210),
                    new BigDecimal(60), caomeiAmount);
            BigDecimal milanShippingRates = PromotionCalculateResult.shippingRates(new BigDecimal(210),
                    new BigDecimal(60), milanAmount);
            BigDecimal shippingRates = d2cShippingRates.add(caomeiShippingRates).add(milanShippingRates);
            orderDto.setShippingRates(shippingRates);
        }
    }

    /**
     * 优惠券优惠
     *
     * @param coupons
     * @param orderDto
     * @param tick
     */
    protected void initCoupon(String coupons, OrderDto orderDto, boolean tick) {
        orderDto.setCouponId(null);
        orderDto.setCouponAmount(new BigDecimal(0));
        if (StringUtils.isBlank(coupons) || !tick) {
            return;
        }
        Coupon coupon = couponQueryService.findByCode(coupons, orderDto.getMemberId());
        if (coupon == null || !coupon.getStatus().equals(CouponStatus.CLAIMED.name())) {
            return;
        }
        Date now = new Date();
        if (now.after(coupon.getExpireDate()) || now.before(coupon.getEnableDate())) {
            return;
        }
        if (orderDto.getTotalAmount().intValue() < coupon.getNeedAmount()) {
            return;
        }
        if (orderDto.getTotalAmount().compareTo(coupon.getCalAmount(orderDto.getTotalAmount())) < 0) {
            return;
        }
        CouponDef couponDef = couponDefQueryService.findById(coupon.getDefineId());
        List<OrderItemDto> initItems = this.checkValidItem(orderDto);
        List<OrderItemDto> validItems = new ArrayList<>();
        if (this.checkValidCoupon(orderDto, couponDef, initItems, validItems)) {
            orderDto.setCouponId(coupon.getId());
            orderDto.setCouponAmount(new BigDecimal(coupon.getAmount()));
            if (coupon.getType().equals(CouponType.DISCOUNT.name())) {
                BigDecimal validAmout = new BigDecimal(0);
                for (OrderItemDto oi : validItems) {
                    validAmout = validAmout.add(oi.getProductPrice().multiply(new BigDecimal(oi.getQuantity()))
                            .subtract(oi.getPromotionAmount()).subtract(oi.getOrderPromotionAmount()));
                }
                orderDto.setCouponAmount(coupon.getCalAmount(validAmout));
            }
            this.splitCoupon(validItems, orderDto.getCouponAmount());
        }
    }

    /**
     * 排除禁用的明细
     *
     * @param orderDto
     * @return
     */
    private List<OrderItemDto> checkValidItem(OrderDto orderDto) {
        List<OrderItemDto> initItems = new ArrayList<>();
        orderDto.getOrderItems().forEach(item -> {
            if (item.getCoupon() == 1)
                initItems.add(item);
        });
        return initItems;
    }

    /**
     * 检查此券是否可用
     *
     * @param orderDto
     * @param couponDef
     * @param validItems
     * @return
     */
    private boolean checkValidCoupon(OrderDto orderDto, CouponDef couponDef, List<OrderItemDto> initItems,
                                     List<OrderItemDto> validItems) {
        boolean valid = false;
        if (initItems == null || initItems.size() <= 0) {
            return valid;
        }
        if (couponDef.getCheckAssociation().equals(Association.ALL.name())) {
            validItems.addAll(initItems);
            valid = true;
        } else {
            validItems.addAll(this.processValid(initItems, couponDef));
            valid = this.processAmount(couponDef, orderDto.getTotalAmount().intValue(), validItems);
        }
        return valid;
    }

    /**
     * 获取满足条件的明细
     */
    private List<OrderItemDto> processValid(List<OrderItemDto> orderItems, CouponDef couponDef) {
        List<Long> targetIds = new ArrayList<>();
        if (couponDef.getCheckAssociation().equals(CouponRelationType.PRODUCT.name())) {
            orderItems.forEach(item -> targetIds.add(item.getCouponProductId()));
        } else if (couponDef.getCheckAssociation().equals(CouponRelationType.DESIGNER.name())) {
            orderItems.forEach(item -> targetIds.add(item.getCouponShopId()));
        }
        List<CouponDefRelation> relations = couponDefRelationService.findByCidAndTids(targetIds, couponDef.getId(),
                couponDef.getCheckAssociation());
        Map<Long, Long> relMap = new HashMap<>();
        for (CouponDefRelation relation : relations) {
            relMap.put(relation.getTargetId(), relation.getCouponDefId());
        }
        List<OrderItemDto> validItems = new ArrayList<>();
        for (int i = 0; i < orderItems.size(); i++) {
            Long key = targetIds.get(i);
            if (!couponDef.getExclude().booleanValue()) {
                if (relMap.containsKey(key))
                    validItems.add(orderItems.get(i));
            } else {
                if (!relMap.containsKey(key))
                    validItems.add(orderItems.get(i));
            }
        }
        return validItems;
    }

    /**
     * 计算是否满足可用金额
     */
    private boolean processAmount(CouponDef couponDef, int orderAmout, List<OrderItemDto> validItems) {
        BigDecimal amout = new BigDecimal(0);
        for (OrderItemDto oi : validItems) {
            amout = amout.add(oi.getProductPrice().multiply(new BigDecimal(oi.getQuantity()))
                    .subtract(oi.getPromotionAmount()).subtract(oi.getOrderPromotionAmount()));
        }
        if (!amout.equals(new BigDecimal(0)) && amout.intValue() >= couponDef.getNeedAmount()
                && orderAmout >= couponDef.getNeedAmount()) {
            return true;
        }
        return false;
    }

    /**
     * 拆分优惠券
     *
     * @param validOrderItems
     * @param couponAmount
     */
    private void splitCoupon(List<OrderItemDto> validOrderItems, BigDecimal couponAmount) {
        if (couponAmount.compareTo(new BigDecimal(0)) > 0) {
            if (validOrderItems.size() == 1) {
                OrderItemDto orderItem = validOrderItems.get(0);
                // 只有一条，直接取，否则会出现小数点
                orderItem.setCouponAmount(couponAmount);
            } else {
                // 计算所有可用商品的总金额
                BigDecimal totalAmount = new BigDecimal(0);
                for (int i = 0; i < validOrderItems.size(); i++) {
                    OrderItemDto orderItem = validOrderItems.get(i);
                    totalAmount = totalAmount.add(orderItem.getTotalPrice()
                            .subtract(orderItem.getOrderPromotionAmount()).subtract(orderItem.getPromotionAmount()));
                }
                // 计算所有可用商品的拆分金额
                BigDecimal splitCouponPromotionAmount = new BigDecimal(0);
                for (int i = 0; i < validOrderItems.size(); i++) {
                    OrderItemDto orderItem = validOrderItems.get(i);
                    if (i == validOrderItems.size() - 1) {
                        BigDecimal couponPromotionAmount = couponAmount.subtract(splitCouponPromotionAmount);
                        orderItem.setCouponAmount(couponPromotionAmount);
                    } else {
                        BigDecimal itemAmount = orderItem.getTotalPrice().subtract(orderItem.getPromotionAmount())
                                .subtract(orderItem.getOrderPromotionAmount());
                        BigDecimal ratePromotion = itemAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP);
                        BigDecimal couponPromotionAmount = couponAmount.multiply(ratePromotion).setScale(2,
                                BigDecimal.ROUND_HALF_UP);
                        orderItem.setCouponAmount(couponPromotionAmount);
                        splitCouponPromotionAmount = splitCouponPromotionAmount.add(couponPromotionAmount);
                    }
                }
            }
        }
    }

    /**
     * 红包优惠
     *
     * @param redPacket
     * @param orderDto
     * @param memberInfo
     * @param tick
     */
    protected void initRedPacks(Integer redPacket, OrderDto orderDto, MemberInfo memberInfo, boolean tick) {
        orderDto.setRedAmount(new BigDecimal(0));
        Account account = accountService.findByMemberId(orderDto.getMemberId());
        if (redPacket == null || redPacket == 0 || !tick) {
            return;
        }
        if (account.getRedDate() != null && account.getRedDate().before(new Date())) {
            return;
        }
        BigDecimal tempAmount = orderDto.getTotalAmount().subtract(orderDto.getCouponAmount());
        if (tempAmount.compareTo(account.getRedAmount()) <= 0) {
            orderDto.setRedAmount(orderDto.getTotalAmount().subtract(orderDto.getCouponAmount()));
        } else {
            orderDto.setRedAmount(account.getRedAmount());
        }
        this.splitRedPackets(orderDto.getOrderItems(), orderDto.getRedAmount());
    }

    /**
     * 拆分红包金额
     *
     * @param orderItems
     * @param redAmount
     */
    private void splitRedPackets(List<OrderItemDto> orderItems, BigDecimal redAmount) {
        if (redAmount.compareTo(new BigDecimal(0)) > 0) {
            if (orderItems.size() == 1) {
                OrderItemDto orderItem = orderItems.get(0);
                // 只有一条，直接取，否则会出现小数点
                orderItem.setRedAmount(redAmount);
            } else {
                // 计算所有可用商品的总金额
                BigDecimal totalAmount = new BigDecimal(0);
                for (int i = 0; i < orderItems.size(); i++) {
                    OrderItemDto orderItem = orderItems.get(i);
                    totalAmount = totalAmount.add(orderItem.getTotalPrice().subtract(orderItem.getPromotionAmount())
                            .subtract(orderItem.getOrderPromotionAmount()));
                }
                // 计算所有可用商品的拆分金额
                BigDecimal splitRedAmount = new BigDecimal(0);
                for (int i = 0; i < orderItems.size(); i++) {
                    OrderItemDto orderItem = orderItems.get(i);
                    if (i == orderItems.size() - 1) {
                        BigDecimal redPromotionAmount = redAmount.subtract(splitRedAmount);
                        orderItem.setRedAmount(redPromotionAmount);
                    } else {
                        BigDecimal itemAmount = orderItem.getTotalPrice().subtract(orderItem.getPromotionAmount())
                                .subtract(orderItem.getOrderPromotionAmount());
                        BigDecimal ratePromotion = itemAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP);
                        BigDecimal redPromotionAmount = redAmount.multiply(ratePromotion).setScale(2,
                                BigDecimal.ROUND_HALF_UP);
                        orderItem.setRedAmount(redPromotionAmount);
                        splitRedAmount = splitRedAmount.add(redPromotionAmount);
                    }
                }
            }
        }
    }

    /**
     * 设置D+店信息
     *
     * @param orderDto
     * @param memberInfo
     */
    protected void initDPlus(OrderDto orderDto, MemberInfo memberInfo) {
        Store store = storeService.findById(memberInfo.getStoreId());
        if (store != null && store.getBusType().equals(BusTypeEnum.DPLUS.name())) {
            for (OrderItemDto item : orderDto.getOrderItems()) {
                item.setDplusId(store.getId());
                item.setDplusCode(store.getCode());
                item.setDplusName(store.getName());
            }
        }
    }

    /**
     * 购物车删除
     *
     * @param cart
     * @param skuId
     * @return
     */
    private List<Long> structCartItemIds(Cart cart, Long[] skuId) {
        List<Long> cartItemIds = new ArrayList<>();
        if (cart != null && cart.getItemMapBySku() != null) {
            for (int i = 0; i < skuId.length; i++) {
                CartItemDto oldCartItem = cart.getItemMapBySku().get(skuId[i]);
                if (oldCartItem != null) {
                    cartItemIds.add(oldCartItem.getId());
                }
            }
        }
        return cartItemIds;
    }

    /**
     * 去支付页面
     *
     * @param orderSn
     * @param model
     * @return
     */
    @RequestMapping(value = "/payment/{orderSn}", method = RequestMethod.GET)
    public String payment(@PathVariable String orderSn, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("订单不存在！");
        }
        model.put("cod", 0);
        if (order.isWaitPay()) {
            result.put("order", order);
            return "pay/payment";
        } else {
            return "redirect:/member/order/" + orderSn;
        }
    }

    /**
     * 去代付页面
     *
     * @param orderSn
     * @param model
     * @return
     */
    @RequestMapping(value = "/payment/substitute/{orderSn}", method = RequestMethod.GET)
    public String substitute(@PathVariable String orderSn, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Order order = orderService.findByOrderSn(orderSn);
        if (order == null) {
            throw new BusinessException("订单不存在！");
        }
        orderService.updateSubstitute(order.getId(), 1);
        OrderDto dto = orderService.doFindByOrderSnAndMemberInfoId(orderSn, order.getMemberId());
        result.put("order", dto);
        MemberInfo memberInfo = memberInfoService.findById(order.getMemberId());
        result.put("member", memberInfo.toJson());
        return "pay/substitute";
    }

}

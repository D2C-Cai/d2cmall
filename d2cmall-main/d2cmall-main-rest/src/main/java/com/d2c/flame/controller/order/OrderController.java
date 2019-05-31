package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.convert.AppVersionConvert;
import com.d2c.flame.enums.CollageErrorCodeEnum;
import com.d2c.flame.property.HttpProperties;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.MemberCertificationService;
import com.d2c.order.dto.*;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.handle.PromotionCondition;
import com.d2c.order.model.*;
import com.d2c.order.model.CollageGroup.GroupStatus;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.model.CouponDef.Association;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDefRelation.CouponRelationType;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.Store.BusTypeEnum;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.model.BargainPriceDO.BargainStatus;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.order.query.CollageOrderSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.OrderTxService;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.model.OrderItem;
import com.d2c.order.third.kaola.model.UserInfo;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.*;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.RandomUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 订单流程
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/order")
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
    private SettingService settingService;
    @Autowired
    private MemberCertificationService memberCertificationService;
    @Autowired
    private BargainPriceService bargainPriceService;
    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private HttpProperties httpProperties;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private CollageGroupService collageGroupService;
    @Autowired
    private CollageOrderService collageOrderService;

    /**
     * 立即购买
     *
     * @param skuId
     * @param num
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/buynow", method = RequestMethod.POST)
    public ResponseResult buyNow(@RequestParam(required = true) Long[] skuId,
                                 @RequestParam(required = true) Integer[] num, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        boolean tick = (memberInfo.getDistributorId() == null);
        boolean cross = false;
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
            cross = cross | (product.getProductTradeType().equals(ProductTradeType.CROSS.name()));
            this.checkQuantityAndStatus(productSku, product, num[i], appTerminal, appVersion, OrderType.ordinary);
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
        JSONObject orderJson = this.orderJson(orderDto);
        // 跨境商品不能钱包支付
        if (cross) {
            orderJson.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY);
        }
        result.put("order", orderJson);
        // 收货地址，钱包账户，优惠券
        result = this.getOtherInfo(orderDto, result, tick, tick, cross);
        return result;
    }

    /**
     * 拼团商品立即购买
     *
     * @param skuId
     * @param num
     * @param collageId
     * @param groupId
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/collage/buynow", method = RequestMethod.POST)
    public ResponseResult buyNowCollage(@RequestParam(required = true) Long skuId,
                                        @RequestParam(required = true) Integer num, @RequestParam(required = true) Long collageId, Long groupId,
                                        String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
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
        if (AppVersionConvert.convert(appTerminal, appVersion) > 0
                && AppVersionConvert.convert(appTerminal, appVersion) <= 3230) {
            if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                    || product.getSource().equals(ProductSource.KAOLA.name())) {
                throw new BusinessException("当前app版本暂不支持该商品购买，请在小程序购买或请将app升级到最新版本！");
            }
        }
        this.checkQuantityAndStatus(productSku, product, num, appTerminal, appVersion, OrderType.collage);
        // 拼团活动处理
        if (collageId == null) {
            this.checkCollage(memberInfo.getId(), groupId, collageId);
        }
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        // 产生购物车明细
        CartItemDto cartItemDto = this.createCartItemDto(product, productSku, memberInfo, num);
        // 拼团价格处理
        cartItemDto.setPrice(productSku.getCollagePrice());
        PromotionCondition pc = new PromotionCondition();
        BeanUtils.copyProperties(cartItemDto, pc, "goodPromotionId", "orderPromotionId", "flashPromotionId");
        promotionConditions.add(pc);
        // 产生订单明细
        OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
        orderItems.add(orderItemDto);
        // 初始化活动
        PromotionCalculateResult calResult = promotionRunService.getPromotionsByOrder(null, null, promotionConditions);
        this.initPromotion(orderDto, orderItems, calResult);
        JSONObject orderJson = this.orderJson(orderDto);
        // 跨境商品不能钱包支付
        if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())) {
            orderJson.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY);
        }
        result.put("order", orderJson);
        boolean certificate = product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                || product.getSource().equals(ProductSource.KAOLA.name());
        // 收货地址，钱包账户，优惠券
        result = this.getOtherInfo(orderDto, result, false, false, certificate);
        return result;
    }

    /**
     * 判断是否能开团参团<br>
     * 限定条件:
     * <li>同一商品同一人只能在进行中只有一个
     * <li>同商品同人只能买N件
     * <li>同账号一天开N次团
     * <li>同账号一天只能参N次团
     *
     * @param memberId
     * @param group
     * @param promotion
     */
    protected void checkCollage(Long memberId, Long groupId, Long promotionId) {
        // 活动相关
        CollagePromotion promotion = collagePromotionService.findById(promotionId);
        // 活动相关
        if (promotion == null || promotion.isOver() < 1) {
            throw new BusinessException("活动已结束，看看其他的拼团活动吧！");
        }
        if (groupId != null) {
            CollageGroup group = collageGroupService.findById(groupId);
            // 满人或者关闭或者由于未支付而没关闭实际已超时的团队不能参与
            if (group == null || group.getStatus() != GroupStatus.PROCESS.getCode()
                    || group.getEndDate().before(new Date())) {
                throw new BusinessException("该团队不在开团中，请重新选择团队！");
            }
        } else {
            int count = collageGroupService.countGroupByPromotionId(promotion.getId());
            if (count >= promotion.getProductCreatedLimit()) {
                throw new BusinessException(CollageErrorCodeEnum.COLLAGE_1002.getMessage(),
                        CollageErrorCodeEnum.COLLAGE_1002.getCode());
            }
        }
        // 判断对于该用户是否存在正在进行中的，存在则不能参团开团
        CollageOrder order = collageOrderService.findExistProcess(promotion.getId(), memberId);
        if (order != null) {
            throw new BusinessException(CollageErrorCodeEnum.COLLAGE_1001.getMessage(),
                    CollageErrorCodeEnum.COLLAGE_1001.getCode());
        }
        // 判断是否该活动该用户参团成功的次数超过限制
        CollageOrderSearcher searcher = new CollageOrderSearcher();
        searcher.setMemberId(memberId);
        searcher.setPromotionId(promotion.getId());
        searcher.setStatus(CollageOrderStatus.SUCESS.getCode());
        int successCount = collageOrderService.countBySearch(searcher);
        if (successCount >= promotion.getMemberBuyLimit()) {
            throw new BusinessException("您已经成功拼团该商品" + successCount + "次了，超过上限了，看看其他拼团活动吧！",
                    CollageErrorCodeEnum.COLLAGE_1003.getCode());
        }
    }

    /**
     * 砍价商品立即购买
     *
     * @param skuId
     * @param num
     * @param bargainId
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/bargain/buynow", method = RequestMethod.POST)
    public ResponseResult buyNowBargain(@RequestParam(required = true) Long skuId,
                                        @RequestParam(required = true) Integer num, @RequestParam(required = true) String bargainId,
                                        String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
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
        if (AppVersionConvert.convert(appTerminal, appVersion) > 0
                && AppVersionConvert.convert(appTerminal, appVersion) <= 3230) {
            if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                    || product.getSource().equals(ProductSource.KAOLA.name())) {
                throw new BusinessException("当前app版本暂不支持该商品购买，请在小程序购买或请将app升级到最新版本！");
            }
        }
        this.checkQuantityAndStatus(productSku, product, num, appTerminal, appVersion, OrderType.bargain);
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
        JSONObject orderJson = this.orderJson(orderDto);
        // 跨境商品不能钱包支付
        if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())) {
            orderJson.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY);
        }
        result.put("order", orderJson);
        boolean certificate = product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                || product.getSource().equals(ProductSource.KAOLA.name());
        // 收货地址，钱包账户，优惠券
        result = this.getOtherInfo(orderDto, result, false, false, certificate);
        return result;
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
        bargainpromotion.setEndDate(new Date(bargainpromotion.getEndDate().getTime() + 24 * 3600 * 1000));
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
     * 组合商品立即购买
     *
     * @param productCombId
     * @param num
     * @param skuId
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/comb/buynow", method = RequestMethod.POST)
    public ResponseResult buyNowComb(@RequestParam(required = true) Long productCombId,
                                     @RequestParam(required = true) Integer num, @RequestParam(required = true) Long[] skuId, String appTerminal,
                                     String appVersion) {
        ResponseResult result = new ResponseResult();
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
            this.checkQuantityAndStatus(productSku, product, num, appTerminal, appVersion, OrderType.ordinary);
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
        result.put("order", this.orderJson(orderDto));
        // 收货地址，钱包账户，优惠券
        result = this.getOtherInfo(orderDto, result, tick, tick, false);
        return result;
    }

    /**
     * 确认订单
     *
     * @param cartItemIds
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseResult confirmOrder(@RequestParam(required = true) Long[] cartItemIds, String appTerminal,
                                       String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        // 产生订单表单
        OrderDto orderDto = new OrderDto(memberInfo);
        orderDto.setTempId(RandomUtil.getRandomString(32));
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<OrderItemDto> orderItems = new ArrayList<>();
        Cart cart = cartService.findCart(memberInfo.getId());
        List<Long> skuIds = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        for (int i = 0; i < cartItemIds.length; i++) {
            CartItemDto cartItem = cart.getItemMapById().get(cartItemIds[i]);
            if (cartItem == null) {
                continue;
            }
            skuIds.add(cartItem.getProductSkuId());
            productIds.add(cartItem.getProductId());
        }
        Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuIds.toArray(new Long[]{}));
        Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
        boolean tick = (memberInfo.getDistributorId() == null);
        boolean cross = false;
        for (int i = 0; i < cartItemIds.length; i++) {
            // 获取购物车明细
            CartItemDto cartItemDto = cart.getItemMapById().get(cartItemIds[i]);
            if (cartItemDto == null) {
                continue;
            }
            ProductSku productSku = skuMap.get(cartItemDto.getProductSkuId());
            Product product = productMap.get(cartItemDto.getProductId());
            if (productSku == null || product == null) {
                continue;
            }
            tick = tick && (product.getStatus() != 5 && product.getStatus() != -1);
            cross = cross | (product.getProductTradeType().equals(ProductTradeType.CROSS.name()));
            this.checkQuantityAndStatus(productSku, product, cartItemDto.getQuantity(), appTerminal, appVersion,
                    OrderType.ordinary);
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
        JSONObject orderJson = this.orderJson(orderDto);
        // 跨境商品不能钱包支付
        if (cross) {
            orderJson.put("payParams", PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY);
        }
        result.put("order", orderJson);
        // 收货地址，钱包账户，优惠券
        result = this.getOtherInfo(orderDto, result, tick, tick, cross);
        return result;
    }

    /**
     * 收货地址，钱包账户，优惠券
     *
     * @param orderDto
     * @param result
     * @param coupon
     * @param red
     * @return
     */
    protected ResponseResult getOtherInfo(OrderDto orderDto, ResponseResult result, boolean coupon, boolean red,
                                          boolean certificate) {
        // 收货地址
        PageResult<AddressDto> addressPager = addressService.findByMemberInfoId(orderDto.getMemberId(),
                new PageModel(1, 1), null);
        AddressDto address = null;
        if (addressPager.getList().size() > 0) {
            address = addressPager.getList().get(0);
            if (certificate) {
                MemberCertification memberCertification = memberCertificationService.findByName(address.getName(),
                        orderDto.getMemberId());
                result.put("memberCertification",
                        memberCertification == null ? new JSONObject() : memberCertification.toJson());
            }
        }
        result.put("address", address == null ? null : address.toJson());
        // 账户信息
        Account account = accountService.findByMemberId(orderDto.getMemberId());
        if (account != null) {
            JSONObject accountJson = account.toJson();
            if (!red) {
                accountJson.put("isRed", false);
            }
            result.put("account", accountJson);
        }
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
            result.put("coupons", this.couponJson(ableCoupons));
            result.put("disableCoupons", this.couponJson(disableCoupons));
        } else {
            result.put("coupons", new JSONArray());
            disableCoupons.addAll(ableCoupons);
            result.put("disableCoupons", this.couponJson(disableCoupons));
            result.put("reason", "所结算商品中含特殊商品，不支持使用优惠券！");
        }
        return result;
    }

    /**
     * 跨境订单的税仓
     *
     * @param goodsId          商品款号
     * @param skuId            商品条码
     * @param buyAmount        商品数量
     * @param channelSalePrice 活动后价格
     * @param name             收货人姓名
     * @param provinceName     省份名称
     * @param cityName         城市名称
     * @param districtName     地区名称
     * @param address          街道地址
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/kaola/confirm", method = RequestMethod.POST)
    public ResponseResult confirmKaola(@RequestParam(required = true) String[] goodsId,
                                       @RequestParam(required = true) String[] skuId, @RequestParam(required = true) Integer[] buyAmount,
                                       @RequestParam(required = true) BigDecimal[] channelSalePrice, @RequestParam(required = true) String name,
                                       @RequestParam(required = true) String provinceName, @RequestParam(required = true) String cityName,
                                       @RequestParam(required = true) String districtName, @RequestParam(required = true) String address)
            throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        String loginCode = memberInfo.getLoginCode();
        // 测试环境请使用@163.com结尾的账户，正式环境不受限
        if (httpProperties.getMobileUrl().contains("test")) {
            loginCode = loginCode + "@163.com";
        }
        UserInfo userInfo = new UserInfo(loginCode, name, memberInfo.getLoginCode(), address, provinceName, cityName,
                districtName, null);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < goodsId.length; i++) {
            OrderItem oi = new OrderItem(goodsId[i], skuId[i], buyAmount[i], channelSalePrice[i], null);
            orderItemList.add(oi);
        }
        try {
            JSONObject response = KaolaClient.getInstance().orderConfirm(userInfo, orderItemList);
            result.put("kaola", response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
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
    public ResponseResult checkCoupon(Long[] skuIds, Long[] productIds, Long[] designerIds, String coupons) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        Coupon coupon = couponQueryService.findByCode(coupons, member.getId());
        if (coupon == null || !coupon.getStatus().equals(CouponStatus.CLAIMED.name())) {
            throw new BusinessException("优惠券不存在！");
        }
        CouponDef couponDef = couponDefQueryService.findById(coupon.getDefineId());
        Long[] targetIds = null;
        String relationType = null;
        if (couponDef.getCheckAssociation().equals(Association.ALL.name())) {
            result.put("skuIds", skuIds);
            return result;
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
        return result;
    }

    /**
     * 创建订单
     *
     * @param skuId         条码
     * @param quantity      数量
     * @param productCombId 组合商品
     * @param warehouseId   仓库号
     * @param warehouseName 仓库名称
     * @param taxAmount     税费
     * @param freight       运费
     * @param addressId     收货地址
     * @param address       收货地址
     * @param coupons       优惠券编码
     * @param memo          买家备注
     * @param drawee        收票人
     * @param redPacket     使用红包
     * @param longitude     经度
     * @param latitude      维度
     * @param bargainId     砍价活动
     * @param collageId     拼团活动
     * @param appTerminal   设备
     * @param appVersion    版本号
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult createOrder(Long[] skuId, Integer[] quantity, Long[] productCombId, Long[] warehouseId,
                                      String[] warehouseName, BigDecimal[] taxAmount, BigDecimal freight, Long addressId, AddressDto address,
                                      String coupons, String memo, String drawee, Integer redPacket, BigDecimal longitude, BigDecimal latitude,
                                      String bargainId, Long collageId, String appTerminal, String appVersion, Long parent_id, Integer level) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
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
            // 产生订单
            OrderDto orderDto = new OrderDto(memberInfo);
            orderDto.setReciverInfo(address);
            orderDto.setMemo(memo);
            orderDto.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
            orderDto.setAppVersion(appVersion);
            orderDto.setDrawee(drawee);
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
            boolean cross = false;
            boolean kaola = false;
            boolean caomei = false;
            boolean milan = false;
            boolean qualified = false;
            OrderType orderType = OrderType.ordinary;
            BigDecimal orderTaxAmount = new BigDecimal(0);
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
                if (product.getStatus() == 5) {
                    tick = tick & false;
                    orderType = OrderType.seckill;
                    orderDto.setType(orderType.name());
                }
                // 礼包商品
                if (product.getStatus() == -1) {
                    tick = tick & false;
                    qualified = qualified | true;
                }
                // 跨境商品
                if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())) {
                    cross = cross | true;
                    orderDto.setCrossBorder(1);
                }
                // 考拉商品
                if (product.getSource().equals(ProductSource.KAOLA.name())) {
                    kaola = kaola | true;
                }
                // 草莓商品
                if (product.getSource().equals(ProductSource.CAOMEI.name())) {
                    caomei = caomei | true;
                }
                // 米兰商品
                if (product.getSource().equals(ProductSource.MILAN.name())) {
                    milan = milan | true;
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
                this.checkQuantityAndStatus(productSku, product, cartItemDto.getQuantity(), appTerminal, appVersion,
                        orderType);
                PromotionCondition pc = new PromotionCondition(cartItemDto, product, productSku, promotion);
                promotionConditions.add(pc);
                // 产生订单明细
                OrderItemDto orderItemDto = this.createOrderItemDto(cartItemDto, product, productSku, memberInfo);
                if (isComb(productCombId)) {
                    pc.setProductCombId(productCombId[0]);
                    orderItemDto.setProductCombId(productCombId[0]);
                }
                if (isWarehouse(warehouseId, warehouseName)) {
                    orderItemDto.setWarehouseId(warehouseId[i]);
                    orderItemDto.setWarehouseName(warehouseName[i]);
                }
                if (isTax(taxAmount)) {
                    if (taxAmount[i] != null && taxAmount[i].compareTo(new BigDecimal(0)) < 0) {
                        result.setStatus(-1);
                        result.setMsg("订单税费异常，请重新下单或联系客服!");
                        return result;
                    }
                    orderItemDto.setTaxAmount(taxAmount[i]);
                    orderTaxAmount = orderTaxAmount.add(taxAmount[i]);
                }
                orderItems.add(orderItemDto);
            }
            orderDto.setTaxAmount(orderTaxAmount);
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
                orderDto = orderService.doInitPartner(memberInfo, orderDto, parent_id, level, qualified);
            } else {
                // 设置D+店信息
                this.initDPlus(orderDto, memberInfo);
            }
            // 考拉或草莓 跨境商品实名认证
            if (cross || kaola || caomei || milan) {
                if ((caomei && AppVersionConvert.convert(appTerminal, appVersion) < 3310)
                        || (milan && AppVersionConvert.convert(appTerminal, appVersion) < 3310)) {
                    result.setStatus(-1);
                    result.setMsg("您的APP版本过低，请先升级再购买该商品！");
                    return result;
                }
                MemberCertification memberCertification = memberCertificationService.findByName(orderDto.getReciver(),
                        memberInfo.getId());
                if (memberCertification == null) {
                    result.setStatus(-3);
                    result.setMsg("您购买的商品需要实名认证！");
                    return result;
                }
                // 考拉订单推单
                if (kaola) {
                    if (freight != null && freight.compareTo(new BigDecimal(0)) >= 0) {
                        orderDto.setShippingRates(orderDto.getShippingRates().add(freight));
                    } else {
                        result.setStatus(-1);
                        result.setMsg("订单运费异常，请重新下单或联系客服!");
                        return result;
                    }
                    this.push2Kaola(memberInfo.getLoginCode(), memberCertification, orderDto);
                }
                result.put("memberCertification",
                        memberCertification == null ? new JSONObject() : memberCertification.toJson());
            }
            // 此处创建订单
            orderDto = orderTxService.doCreateOrder(orderDto, coupons, redPacket);
            // 清空购物车
            Cart cart = cartService.findCart(memberInfo.getId());
            List<Long> delCartItemIds = structCartItemIds(cart, skuId);
            if (delCartItemIds != null && delCartItemIds.size() > 0) {
                cartService.deleteCartItems(delCartItemIds, memberInfo.getId());
            }
            // 冻结砍价单
            if (!StringUtils.isEmpty(bargainId)) {
                bargainPriceService.updateBargainForPay(bargainId);
            }
            result.put("order", orderDto);
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
            return result;
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

    /**
     * 根据地址返回认证信息
     *
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/certification/{addressId}", method = RequestMethod.GET)
    public ResponseResult getCertification(@PathVariable("addressId") Long addressId) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        Address address = addressService.findById(addressId);
        if (address == null || !address.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("地址非本人，请重选");
        }
        MemberCertification certification = memberCertificationService.findByName(address.getName(),
                memberInfo.getId());
        if (certification != null) {
            result.put("memberCertification", certification.toJson());
        }
        return result;
    }

    private void push2Kaola(String loginCode, MemberCertification memberCertification, OrderDto orderDto) {
        // 测试环境请使用@163.com结尾的账户，正式环境不受限
        if (httpProperties.getMobileUrl().contains("test")) {
            loginCode = loginCode + "@163.com";
        }
        UserInfo userInfo = new UserInfo(loginCode, memberCertification.getRealName(), orderDto.getContact(),
                orderDto.getAddress(), orderDto.getProvince(), orderDto.getCity(), orderDto.getDistrict(),
                memberCertification.getIdentityCard());
        String domain = "https://img.d2c.cn";
        if (StringUtil.isNotBlank(memberCertification.getFrontPic())) {
            userInfo.setIdentityPicFront(domain + memberCertification.getFrontPic());
        }
        if (StringUtil.isNotBlank(memberCertification.getBehindPic())) {
            userInfo.setIdentityPicBack(domain + memberCertification.getBehindPic());
        }
        Map<Long, List<OrderItem>> pushOrders = new HashMap<>();
        for (OrderItemDto dto : orderDto.getOrderItems()) {
            if (dto.getProductSource() != null && dto.getProductSource().equals(ProductSource.KAOLA.name())) {
                OrderItem item = new OrderItem(dto.getProductSn(), dto.getProductSkuSn(), dto.getProductQuantity(),
                        dto.getProductPrice(), dto.getWarehouseId().toString());
                if (pushOrders.get(dto.getWarehouseId()) == null) {
                    pushOrders.put(dto.getWarehouseId(), new ArrayList<OrderItem>());
                }
                pushOrders.get(dto.getWarehouseId()).add(item);
            }
        }
        for (Map.Entry<Long, List<OrderItem>> entry : pushOrders.entrySet()) {
            try {
                if (pushOrders.entrySet().size() > 0) {
                    KaolaClient.getInstance().bookOrder(orderDto.getOrderSn() + "-" + entry.getKey(), userInfo,
                            entry.getValue());
                }
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
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
    private void checkQuantityAndStatus(ProductSku productSku, Product product, Integer quantity, String appTerminal,
                                        String appVersion, OrderType orderType) {
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
        if (AppVersionConvert.convert(appTerminal, appVersion) > 0
                && AppVersionConvert.convert(appTerminal, appVersion) <= 3230) {
            if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                    || product.getSource().equals(ProductSource.KAOLA.name())) {
                throw new BusinessException("当前app版本暂不支持该商品购买，请在小程序购买或请将app升级到最新版本！");
            }
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
     * 是否是收税商品
     *
     * @param taxAmount
     * @return
     */
    private boolean isTax(BigDecimal[] taxAmount) {
        return taxAmount != null && taxAmount.length > 0;
    }

    /**
     * 是否是仓库商品
     *
     * @param warehouseId
     * @param warehouseName
     * @return
     */
    private boolean isWarehouse(Long[] warehouseId, String[] warehouseName) {
        return warehouseId != null && warehouseName != null && warehouseId.length > 0 && warehouseName.length > 0;
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
     * 优惠券处理
     *
     * @param coupons
     * @return
     */
    private JSONArray couponJson(List<Coupon> coupons) {
        JSONArray array = new JSONArray();
        if (coupons != null && coupons.size() > 0) {
            coupons.forEach(item -> array.add(item.toJson()));
        }
        return array;
    }

    /**
     * 订单处理
     *
     * @param order
     * @return
     */
    private JSONObject orderJson(OrderDto order) {
        JSONObject obj = order.toJson();
        JSONArray items = new JSONArray();
        order.getOrderItems().forEach(item -> items.add(item.toJson()));
        obj.put("items", items);
        return obj;
    }

}

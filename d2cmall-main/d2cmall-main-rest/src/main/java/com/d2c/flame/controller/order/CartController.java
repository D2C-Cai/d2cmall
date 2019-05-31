package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.convert.AppVersionConvert;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberCollection;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.service.MemberCollectionService;
import com.d2c.order.dto.Cart;
import com.d2c.order.dto.CartItemDto;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.handle.PromotionCondition;
import com.d2c.order.model.CartItem;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.order.service.CartService;
import com.d2c.order.service.PromotionRunService;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 购物车
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/cart")
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private PromotionRunService promotionRunService;
    @Autowired
    private MemberCollectionService memberCollectionService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    /**
     * 购物车列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Cart cart = cartService.findCart(memberInfo.getId());
        Iterator<Entry<Long, CartItemDto>> iterator = cart.getItemMapById().descendingMap().entrySet().iterator();
        JSONArray array = new JSONArray();
        List<Long> skuIds = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        while (iterator.hasNext()) {
            Entry<Long, CartItemDto> entry = iterator.next();
            CartItemDto cartItem = entry.getValue();
            skuIds.add(cartItem.getProductSkuId());
            productIds.add(cartItem.getProductId());
        }
        Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuIds.toArray(new Long[]{}));
        Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
        iterator = cart.getItemMapById().descendingMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Long, CartItemDto> entry = iterator.next();
            CartItemDto cartItem = entry.getValue();
            ProductSku productSku = skuMap.get(cartItem.getProductSkuId());
            Product product = productMap.get(cartItem.getProductId());
            if (productSku == null || product == null) {
                continue;
            }
            cartItem.setProductSku(productSku);
            if (!productSku.getPrice().equals(cartItem.getPrice())
                    || !productSku.getOriginalCost().equals(cartItem.getOriginalPrice())) {
                cartItem.setPrice(productSku.getPrice());
                cartItem.setOriginalPrice(productSku.getOriginalCost());
            }
            PromotionCondition pc = new PromotionCondition(cartItem, product, productSku, true);
            PromotionCalculateItem calItem = promotionRunService.getPromotionByItem(memberInfo.getDistributorId(), pc);
            cartItem = cartItem.initPromotion(calItem);
            array.add(cartItem.toJson());
        }
        result.put("compareStore", 3);
        result.put("cart", array);
        return result;
    }

    /**
     * 加入购物车
     *
     * @param skuId
     * @param num
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insert(@RequestParam(required = true) Long skuId, @RequestParam(required = true) Integer num,
                                 String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        Cart cart = cartService.findCart(member.getId());
        this.addCartItem(skuId, num, appTerminal, appVersion, result, member, cart);
        // 加入购物车任务
        memberTaskExecService.taskDone(member.getId(), MemberTaskEnum.CART_ADD);
        return result;
    }

    /**
     * 批量加入购物车
     *
     * @param skuIds
     * @param nums
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/batch/insert", method = RequestMethod.POST)
    public ResponseResult insertBatch(@RequestParam(required = true) Long[] skuIds,
                                      @RequestParam(required = true) Integer[] nums, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        Cart cart = cartService.findCart(member.getId());
        for (int i = 0; i < skuIds.length; i++) {
            try {
                this.addCartItem(skuIds[i], nums[i], appTerminal, appVersion, result, member, cart);
            } catch (BusinessException e) {
                continue;
            }
        }
        // 加入购物车任务
        memberTaskExecService.taskDone(member.getId(), MemberTaskEnum.CART_ADD);
        return result;
    }

    private void addCartItem(Long skuId, Integer num, String appTerminal, String appVersion, ResponseResult result,
                             MemberInfo member, Cart cart) {
        if (num == null || num < 1) {
            throw new BusinessException("购物车商品数量为0！");
        }
        ProductSku productSku = productSkuService.findById(skuId);
        if (productSku == null) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        Product product = productService.findById(productSku.getProductId());
        // 当前商品下架或者删除，不能添加购物车
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
        CartItem oldCartItem = cart.getItemMapBySku().get(skuId);
        // 生成或更新购物车明细
        if (oldCartItem == null) {
            // 当前没有销售的库存了，不能添加购物车
            this.checkFlashStock(productSku, product, num);
            if (productSku.getAvailableStore() < num) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，库存不足，下次早点来哦！");
            }
            CartItem cartItem = new CartItem(product, productSku, member);
            cartItem.setQuantity(num);
            cartItem.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
            cartItem.setAppVersion(appVersion);
            cartItem = cartService.insert(cartItem, member.getId());
            CartItemDto dto = new CartItemDto();
            BeanUtils.copyProperties(cartItem, dto);
            cart.getItemMapBySku().put(cartItem.getProductSkuId(), dto);
            result.put("cartItemId", cartItem.getId());
        } else {
            // 当前没有销售的库存了，不能添加购物车
            this.checkFlashStock(productSku, product, oldCartItem.getQuantity() + num);
            if (productSku.getAvailableStore() < oldCartItem.getQuantity() + num) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，已经被主人全部加入购物车了哦！");
            }
            cartService.updateQuantity(skuId, member.getId(), oldCartItem.getQuantity() + num);
            cart.getItemMapBySku().get(skuId).setQuantity(oldCartItem.getQuantity() + num);
            result.put("cartItemId", oldCartItem.getId());
        }
        cart.getItemMapBySku().get(skuId).setProductSku(productSku);
        // 购物车有几件商品
        result.put("countNum", cart.getNumCount());
        // 购物车有几种商品
        result.put("cartItemCount", cart.getTypeCount());
    }

    private void checkFlashStock(ProductSku productSku, Product product, Integer quantity) {
        if (product.getFlashPromotionId() != null) {
            FlashPromotion flashPromotion = flashPromotionService.findById(product.getFlashPromotionId());
            if (flashPromotion != null && !flashPromotion.isOver()) {
                if (productSku.getAvailableFlashStore() < quantity) {
                    throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                            + "颜色：" + productSku.getColorValue() + "，库存不足，下次早点来哦！");
                }
            }
        }
    }

    /**
     * 购物车商品种类数量
     *
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseResult count() {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        int cartItemCount = cartService.countByMemberId(member.getId());
        result.put("cartItemCount", cartItemCount);
        return result;
    }

    /**
     * 删除购物车项，清空失效宝贝
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(Long[] ids) {
        MemberInfo member = this.getLoginMemberInfo();
        for (Long id : ids) {
            if (id != null) {
                cartService.deleteCartItem(id, member.getId());
            }
        }
        return this.list();
    }

    /**
     * 加入到我的收藏
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/move/collection", method = RequestMethod.POST)
    public ResponseResult collection(Long[] ids) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        for (Long id : ids) {
            CartItem cartItem = cartService.findById(id);
            MemberCollection memberCollection = new MemberCollection();
            memberCollection.setMemberId(memberInfo.getId());
            memberCollection.setNickName(memberInfo.getDisplayName());
            memberCollection.setHeadPic(memberInfo.getHeadPic());
            memberCollection.setProductId(cartItem.getProductId());
            memberCollection.setProductName(cartItem.getProductName());
            memberCollection.setProductInernalSN(cartItem.getProductSn());
            memberCollection.setProductPic(cartItem.getProductImg());
            memberCollection.setDesigners(cartItem.getDesignerName());
            memberCollection.setProductPrice(cartItem.getPrice());
            SearcherProduct searcherProduct = productSearcherQueryService
                    .findById(String.valueOf(cartItem.getProductId()));
            if (searcherProduct != null) {
                memberCollection.setProductPrice(searcherProduct.getCurrentPrice());
            }
            try {
                memberCollectionService.insert(memberCollection);
            } catch (BusinessException e) {
            }
            cartService.deleteCartItem(id, memberInfo.getId());
        }
        return this.list();
    }

    /**
     * 修改商品数量
     *
     * @param id
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseResult quantity(@PathVariable Long id, @RequestParam(required = true) Integer quantity) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        CartItemDto cartItem = cartService.findOneByMemberId(id, member.getId());
        if (cartItem == null) {
            throw new BusinessException("该购物车的宝贝不存在");
        }
        ProductSku productSku = productSkuService.findById(cartItem.getProductSkuId());
        Product product = productService.findById(cartItem.getProductId());
        // 当前没有销售的库存了，不能添加购物车，减库存不能判断
        this.checkFlashStock(productSku, product, quantity);
        if (quantity > cartItem.getQuantity() && productSku.getAvailableStore() < quantity) {
            throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue() + "颜色："
                    + productSku.getColorValue() + "，库存不足，下次早点来哦！");
        }
        cartService.updateQuantity(cartItem.getProductSkuId(), member.getId(), quantity);
        PromotionCondition pc = new PromotionCondition(cartItem, product, productSku, true);
        PromotionCalculateItem calItem = promotionRunService.getPromotionByItem(member.getDistributorId(), pc);
        // 历史遗留，实际为单品总优惠
        result.put("promotionPrice", calItem.getPromotionAmount());
        result.put("availableStore", productSku.getAvailableStore());
        result.put("quantity", quantity);
        return result;
    }

    /**
     * 计算购物车金额
     *
     * @param cartItemIds
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ResponseResult calculate(Long[] cartItemIds, Integer[] quantity) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        JSONObject obj = new JSONObject();
        Cart cart = cartService.findCart(member.getId());
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<Long> skuIds = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        if (cartItemIds != null && cartItemIds.length > 0) {
            for (int i = 0; i < cartItemIds.length; i++) {
                CartItemDto cartItem = cart.getItemMapById().get(cartItemIds[i]);
                if (cartItem == null) {
                    continue;
                }
                skuIds.add(cartItem.getProductSkuId());
                productIds.add(cartItem.getProductId());
            }
        }
        Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuIds.toArray(new Long[]{}));
        Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
        if (cartItemIds != null && cartItemIds.length > 0) {
            for (int i = 0; i < cartItemIds.length; i++) {
                CartItemDto cartItem = cart.getItemMapById().get(cartItemIds[i]);
                if (cartItem == null) {
                    continue;
                }
                ProductSku productSku = skuMap.get(cartItem.getProductSkuId());
                Product product = productMap.get(cartItem.getProductId());
                if (productSku == null || product == null) {
                    continue;
                }
                cartItem.setProductSku(productSku);
                if (!productSku.getPrice().equals(cartItem.getPrice())
                        || !productSku.getOriginalCost().equals(cartItem.getOriginalPrice())) {
                    cartItem.setPrice(productSku.getPrice());
                    cartItem.setOriginalPrice(productSku.getOriginalCost());
                }
                cartItem.setQuantity(quantity[i]);
                if (productSku.getAvailableStore() < cartItem.getQuantity()) {
                    cartItem.setQuantity(productSku.getAvailableStore());
                } else {
                    cartItem.setQuantity(cartItem.getQuantity());
                }
                PromotionCondition pc = new PromotionCondition(cartItem, product, productSku, true);
                promotionConditions.add(pc);
            }
            PromotionCalculateResult calculate = promotionRunService.getPromotionsByOrder(member.getDistributorId(),
                    null, promotionConditions);
            JSONArray array = new JSONArray();
            for (PromotionCalculateItem calItem : calculate.getItems()) {
                Map<Long, CartItemDto> itemMapById = cart.getItemMapById();
                if (itemMapById.containsKey(calItem.getKey())) {
                    CartItemDto cartItem = itemMapById.get(calItem.getKey());
                    cartItem = cartItem.initPromotion(calItem);
                    JSONObject item = cartItem.toJson();
                    array.add(item);
                }
            }
            obj.put("items", array);
            obj.put("promotionAmount", calculate.getOrderPromotionAmount());
            obj.put("totalAmount", calculate.getTotalAmount());
            obj.put("shippingRates", new BigDecimal(0));
        }
        result.put("cart", obj);
        return result;
    }

    /**
     * 修改购物车商品
     *
     * @param cartItemId
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/update/sku", method = RequestMethod.POST)
    public ResponseResult updateSku(Long cartItemId, Long skuId) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        ProductSku productSku = productSkuService.findById(skuId);
        if (productSku == null) {
            throw new BusinessException("商品已下架，添加不成功！");
        }
        Product product = productService.findById(productSku.getProductId());
        CartItem cartItem = cartService.findById(cartItemId);
        // 当前商品下架或者删除，不能添加购物车
        if (product == null || product.getMark() <= 0) {
            throw new BusinessException("商品已下架，添加不成功！");
        }
        // 查找该用户是否已有这个库存了
        CartItem newCartItem = cartService.findBySkuAndMember(skuId, member.getId());
        if (newCartItem != null) {
            newCartItem.setQuantity(newCartItem.getQuantity() + cartItem.getQuantity());
            this.checkFlashStock(productSku, product, newCartItem.getQuantity());
            if (productSku.getAvailableStore() < newCartItem.getQuantity()) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，库存不足，下次早点来哦！");
            }
            cartService.mergeSku(cartItemId, skuId, newCartItem.getQuantity(), member.getId());
        } else {
            this.checkFlashStock(productSku, product, cartItem.getQuantity());
            if (productSku.getAvailableStore() < cartItem.getQuantity()) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，库存不足，下次早点来哦！");
            }
            cartService.updateSku(cartItemId, skuId, member.getId());
        }
        return result;
    }

}

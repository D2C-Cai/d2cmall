package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.Cart;
import com.d2c.order.dto.CartItemDto;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.handle.PromotionCondition;
import com.d2c.order.model.CartItem;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.order.service.CartService;
import com.d2c.order.service.PromotionRunService;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private PromotionRunService promotionRunService;

    /**
     * 获取购物车数量(商品总件数)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String count(ModelMap model) {
        int cartItemCount = 0;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            Cart cart = cartService.findCart(memberInfo.getId());
            cartItemCount = cart.getNumCount();
        } catch (Exception e) {
            cartItemCount = 0;
        }
        model.put("cartItemCount", cartItemCount);
        return "";
    }

    /**
     * 购物车列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Cart cart = cartService.findCart(memberInfo.getId());
        Iterator<Entry<Long, CartItemDto>> iterator = cart.getItemMapById().descendingMap().entrySet().iterator();
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
        }
        model.put("cart", cart);
        return "order/cart_list";
    }

    /**
     * 购物车列表(鼠标移上去弹出)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list/show", method = RequestMethod.GET)
    public String showList(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Cart cart = cartService.findCart(memberInfo.getId());
        result.put("cart", cart);
        return "";
    }

    /**
     * 加入购物车
     *
     * @param skuId
     * @param quantity
     * @param model
     * @return
     */
    @RequestMapping(value = "/add/{skuId}/{quantity}", method = {RequestMethod.POST, RequestMethod.GET})
    public String insert(@PathVariable Long skuId, @PathVariable int quantity, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Cart cart = cartService.findCart(memberInfo.getId());
        cart = this.addCartItem(cart, skuId, quantity, memberInfo, result);
        // 预计金额计算
        Map<String, Object> map = this.calculateCart(cart, memberInfo);
        result.put("countNum", cart.getTypeCount());
        result.put("totalNum", map.get("totalNum"));
        result.put("totalAmount", map.get("totalAmount"));
        return "";
    }

    private Cart addCartItem(Cart cart, Long skuId, int quantity, MemberInfo member, SuccessResponse result) {
        if (quantity < 1) {
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
        if (product.getProductTradeType().equals(ProductTradeType.CROSS.name())
                || product.getSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("该商品暂仅支持app和小程序购买！");
        }
        CartItem oldCartItem = cart.getItemMapBySku().get(skuId);
        // 生成或更新购物车明细
        if (oldCartItem == null) {
            CartItem cartItem = new CartItem(product, productSku, member);
            cartItem.setQuantity(quantity);
            // 当前没有销售的库存了, 不能添加购物车
            if (productSku.getAvailableStore() < cartItem.getQuantity()) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "颜色：" + productSku.getColorValue() + "，库存不足，下次早点来哦！");
            }
            cartItem.setDevice(isMobileDevice() ? DeviceTypeEnum.MOBILE.toString() : DeviceTypeEnum.PC.toString());
            cartItem.setAppVersion(VERSION);
            cartItem = cartService.insert(cartItem, member.getId());
            CartItemDto dto = new CartItemDto();
            BeanUtils.copyProperties(cartItem, dto);
            dto.initCondition(product.getGoodPromotionId(), product.getOrderPromotionId(),
                    product.getFlashPromotionId(), productSku.getaPrice(), productSku.getFlashPrice(),
                    productSku.getAvailableFlashStore());
            cart.getItemMapBySku().put(cartItem.getProductSkuId(), dto);
        } else {
            // 当前没有销售的库存了, 不能添加购物车
            if (productSku.getAvailableStore() < (oldCartItem.getQuantity() + quantity)) {
                throw new BusinessException(("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "，颜色：" + productSku.getColorValue() + "，库存仅" + productSku.getAvailableStore()
                        + "件，已经被主人全部加入购物车了哦~"));
            }
            cartService.updateQuantity(skuId, member.getId(), oldCartItem.getQuantity() + quantity);
            cart.getItemMapBySku().get(skuId).setQuantity(oldCartItem.getQuantity() + quantity);
        }
        return cart;
    }

    /**
     * 获取购物车的商品件数和总价
     *
     * @param cart
     * @param member
     * @return
     */
    private Map<String, Object> calculateCart(Cart cart, MemberInfo member) {
        Map<String, Object> map = new HashMap<>();
        // 预计金额计算
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        Iterator<Entry<Long, CartItemDto>> iterator = cart.getItemMapBySku().entrySet().iterator();
        int totalCount = 0;
        while (iterator.hasNext()) {
            Entry<Long, CartItemDto> entry = iterator.next();
            CartItemDto cartItem = entry.getValue();
            PromotionCondition pc = new PromotionCondition();
            BeanUtils.copyProperties(cartItem, pc);
            // 活动预处理
            PromotionCalculateItem calItem = promotionRunService.getPromotionByItem(member.getDistributorId(), pc);
            cartItem = cartItem.initPromotion(calItem);
            BeanUtils.copyProperties(cartItem, pc);
            promotionConditions.add(pc);
            totalCount += cartItem.getQuantity();
        }
        PromotionCalculateResult proCalResult = promotionRunService.getPromotionsByOrder(member.getDistributorId(),
                null, promotionConditions);
        map.put("totalNum", totalCount);
        map.put("totalAmount", proCalResult.getTotalAmount());
        return map;
    }

    /**
     * 删除购物车项
     *
     * @param cartItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete/{cartItemId}", method = RequestMethod.GET)
    public String deleteCartItem(@PathVariable Long cartItemId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        cartService.deleteCartItem(cartItemId, memberInfo.getId());
        model.put("result", result);
        return "";
    }

    /**
     * 清空购物车
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String deleteCart(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        cartService.deleteCart(memberInfo.getId());
        return "";
    }

    /**
     * 购物车数量修改
     *
     * @param skuId
     * @param quantity
     * @param model
     * @return
     */
    @RequestMapping(value = "/cartItem/{skuId}", method = RequestMethod.POST)
    public String updateQuantity(@PathVariable Long skuId, int quantity, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        CartItem cartItem = cartService.findBySkuAndMember(skuId, memberInfo.getId());
        ProductSku productSku = productSkuService.findById(skuId);
        // 当前没有销售的库存了, 不能添加购物车
        if (cartItem != null) {
            if (quantity > cartItem.getQuantity() && productSku.getAvailableStore() < quantity) {
                throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue()
                        + "，颜色：" + productSku.getColorValue() + "，商品已售罄，下次早点来哦！");
            }
            cartService.updateQuantity(skuId, memberInfo.getId(), quantity);
        }
        return "";
    }

    /**
     * 购物车动态金额计算
     *
     * @param cartItemId
     * @param quantity
     * @param model
     * @return
     */
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public String calculate(Long[] cartItemId, Integer[] quantity, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Cart cart = cartService.findCart(memberInfo.getId());
        List<IPromotionInterface> promotionConditions = new ArrayList<>();
        List<Long> skuIds = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        if (cartItemId != null && cartItemId.length > 0) {
            for (int i = 0; i < cartItemId.length; i++) {
                CartItemDto cartItem = cart.getItemMapById().get(cartItemId[i]);
                if (cartItem == null) {
                    continue;
                }
                skuIds.add(cartItem.getProductSkuId());
                productIds.add(cartItem.getProductId());
            }
        }
        Map<Long, ProductSku> skuMap = productSkuService.findByIds(skuIds.toArray(new Long[]{}));
        Map<Long, Product> productMap = productService.findByIds(productIds.toArray(new Long[]{}));
        for (int i = 0; cartItemId != null && i < cartItemId.length; i++) {
            CartItemDto cartItem = cart.getItemMapById().get(cartItemId[i]);
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
        PromotionCalculateResult result = promotionRunService.getPromotionsByOrder(memberInfo.getDistributorId(), null,
                promotionConditions);
        model.put("result", result);
        return "order/cart_cal";
    }

}

package com.d2c.order.handle;

import com.d2c.order.dto.CartItemDto;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class PromotionCondition implements IPromotionInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 活动ID
     */
    private Long id;
    /**
     * 销售价
     */
    private BigDecimal productPrice;
    /**
     * 吊牌价
     */
    private BigDecimal originalPrice;
    /**
     * 一口价
     */
    private BigDecimal aPrice;
    /**
     * 限时价
     */
    private BigDecimal flashPrice;
    /**
     * 限时数量
     */
    private Integer flashStock;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 条码ID
     */
    private Long productSkuId;
    /**
     * 商品总金额
     */
    private BigDecimal totalPrice;
    /**
     * 数量
     */
    private int quantity;
    /**
     * 商品活动ID
     */
    private Long goodPromotionId;
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 限时购活动
     */
    private Long flashPromotionId;
    /**
     * 组合商品ID
     */
    private Long productCombId;
    /**
     * 活动优惠金额
     */
    private BigDecimal promotionAmount;

    public PromotionCondition() {
    }

    public PromotionCondition(CartItemDto cartItem, Product product, ProductSku productSku, Boolean promotion) {
        if (promotion) {
            cartItem.initCondition(product.getGoodPromotionId(), product.getOrderPromotionId(),
                    product.getFlashPromotionId(), productSku.getaPrice(), productSku.getFlashPrice(),
                    productSku.getAvailableFlashStore());
        }
        cartItem.setTaxation(product.getTaxation());
        cartItem.setTaxPrice(product.getTaxPrice());
        cartItem.setProductName(product.getName());
        BeanUtils.copyProperties(cartItem, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public BigDecimal getaPrice() {
        return aPrice;
    }

    public void setaPrice(BigDecimal aPrice) {
        this.aPrice = aPrice;
    }

    @Override
    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
    }

    @Override
    public Integer getFlashStock() {
        return flashStock;
    }

    public void setFlashStock(Integer flashStock) {
        this.flashStock = flashStock;
    }

    @Override
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    @Override
    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public Long getGoodPromotionId() {
        return goodPromotionId;
    }

    public void setGoodPromotionId(Long goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    @Override
    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    @Override
    public Long getProductCombId() {
        return productCombId;
    }

    public void setProductCombId(Long productCombId) {
        this.productCombId = productCombId;
    }

    @Override
    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

}

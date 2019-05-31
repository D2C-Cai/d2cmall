package com.d2c.order.handle;

import com.d2c.order.model.base.ICouponInterface;

import java.math.BigDecimal;

public class CouponCondition implements ICouponInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long couponProductId;
    /**
     * 设计师ID
     */
    private Long couponShopId;
    /**
     * 商品销售价
     */
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    private int quantity;
    /**
     * 商品总计优惠金额
     */
    private BigDecimal promotionAmount;

    public Long getCouponProductId() {
        return couponProductId;
    }

    public void setCouponProductId(Long couponProductId) {
        this.couponProductId = couponProductId;
    }

    public Long getCouponShopId() {
        return couponShopId;
    }

    public void setCouponShopId(Long couponShopId) {
        this.couponShopId = couponShopId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

}

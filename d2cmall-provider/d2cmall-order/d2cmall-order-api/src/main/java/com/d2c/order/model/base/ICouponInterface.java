package com.d2c.order.model.base;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券条件
 */
public interface ICouponInterface extends Serializable {

    /**
     * 商品ID
     *
     * @return
     */
    Long getCouponProductId();

    /**
     * 店铺ID
     *
     * @return
     */
    Long getCouponShopId();

    /**
     * 商品价格
     *
     * @return
     */
    BigDecimal getProductPrice();

    /**
     * 商品数量
     *
     * @return
     */
    int getQuantity();

    /**
     * 优惠金额
     *
     * @return
     */
    BigDecimal getPromotionAmount();

}

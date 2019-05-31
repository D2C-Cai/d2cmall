package com.d2c.order.model.base;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活动条件
 */
public interface IPromotionInterface extends Serializable {

    /**
     * 活动ID
     *
     * @return
     */
    Long getId();

    /**
     * 销售价
     *
     * @return
     */
    BigDecimal getProductPrice();

    /**
     * 吊牌价
     *
     * @return
     */
    BigDecimal getOriginalPrice();

    /**
     * 一口价
     *
     * @return
     */
    BigDecimal getaPrice();

    /**
     * 限时价
     *
     * @return
     */
    BigDecimal getFlashPrice();

    /**
     * 限时数量
     *
     * @return
     */
    Integer getFlashStock();

    /**
     * 产品ID
     *
     * @return
     */
    Long getProductId();

    /**
     * 设计师ID
     *
     * @return
     */
    Long getDesignerId();

    /**
     * 商品SKU的ID
     *
     * @return
     */
    Long getProductSkuId();

    /**
     * 商品总金额
     *
     * @return
     */
    BigDecimal getTotalPrice();

    /**
     * 商品数量
     *
     * @return
     */
    int getQuantity();

    /**
     * 单品活动ID
     *
     * @return
     */
    Long getGoodPromotionId();

    /**
     * 订单活动ID
     *
     * @return
     */
    Long getOrderPromotionId();

    /**
     * 限时购活动ID
     *
     * @return
     */
    Long getFlashPromotionId();

    /**
     * 组合商品ID
     *
     * @return
     */
    Long getProductCombId();

}

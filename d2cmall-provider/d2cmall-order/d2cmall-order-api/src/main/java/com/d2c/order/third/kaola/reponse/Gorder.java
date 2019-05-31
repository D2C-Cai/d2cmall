package com.d2c.order.third.kaola.reponse;

import java.math.BigDecimal;

/**
 * 下单 订单信息
 *
 * @author Lain
 */
public class Gorder {

    /**
     * 订单ID
     */
    private String id;
    /**
     * 订单实际需要支付金额
     */
    private BigDecimal gpayAmount;
    /**
     * 订单原始需要支付金额
     */
    private BigDecimal originalGpayAmount;
    /**
     * 订单的总金额
     */
    private BigDecimal gorderAmount;
    /**
     * 短标题
     */
    private String goodsName;
    /**
     * 忽略，以订单状态查询接口返回为准
     */
    private Integer gorderStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getGpayAmount() {
        return gpayAmount;
    }

    public void setGpayAmount(BigDecimal gpayAmount) {
        this.gpayAmount = gpayAmount;
    }

    public BigDecimal getOriginalGpayAmount() {
        return originalGpayAmount;
    }

    public void setOriginalGpayAmount(BigDecimal originalGpayAmount) {
        this.originalGpayAmount = originalGpayAmount;
    }

    public BigDecimal getGorderAmount() {
        return gorderAmount;
    }

    public void setGorderAmount(BigDecimal gorderAmount) {
        this.gorderAmount = gorderAmount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGorderStatus() {
        return gorderStatus;
    }

    public void setGorderStatus(Integer gorderStatus) {
        this.gorderStatus = gorderStatus;
    }

}

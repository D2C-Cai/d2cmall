package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 管易做单明细
 *
 * @author Lain
 */
@Table(name = "o_guanyi_order_item")
public class GuanyiOrderItem extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 指的是管易订单的id
     */
    @AssertColumn("管易订单ID不能为空")
    private Long orderId;
    /**
     * 订单编号（该订单指官网订单编号）
     */
    private String platformCode;
    /**
     * 快递公司
     */
    private String expressName;
    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 管易单据日期
     */
    private Date deliveryDate;
    /**
     * 管易整单code
     */
    private String orderCode;
    /** --------------------------------明细信息------------------------------------------ */
    /**
     * 货号
     */
    private String itemCode;
    /**
     * sku
     */
    private String skuCode;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 支付金额（已扣去优惠）
     */
    private BigDecimal amountAfter;
    /**
     * 明细编号
     */
    private String tradeCode;
    /**
     * 是否需要物流做单
     */
    private Integer express;
    /** ----------------------------------------物流异常信息-------------------------------------------- */
    /**
     * 是否发生过物流异常
     */
    private Integer expressFall = 0;
    /**
     * 更新物流的异常
     */
    private String expressError;
    /**
     * 是否处理
     */
    private Integer expressHandle = 0;
    /**
     * 物流单据处理人
     */
    private String expressHandleMan;
    /**
     * 处理时间
     */
    private Date expressHandleDate;
    /**
     * 处理内容
     */
    private String expressHandleContent;

    public GuanyiOrderItem() {
    }

    public GuanyiOrderItem(JSONObject obj, GuanyiOrder guanyiOrder) {
        this.itemCode = obj.getString("item_code");
        this.skuCode = obj.getString("sku_code");
        this.qty = obj.getInteger("qty");
        this.amountAfter = obj.getBigDecimal("amount_after");
        this.tradeCode = obj.getString("trade_code");
        this.platformCode = obj.getString("platform_code");
        this.express = guanyiOrder.getExpress();
        this.expressName = guanyiOrder.getExpressName();
        this.expressNo = guanyiOrder.getExpressNo();
        this.deliveryDate = guanyiOrder.getDeliveryDate();
        this.orderCode = guanyiOrder.getCode();
        if ("FJH001".equals(this.skuCode) || "HHZ001".equals(this.skuCode) || "RSHK001".equals(this.getSkuCode())) {
            express = 0;
        }
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public Integer getExpressFall() {
        return expressFall;
    }

    public void setExpressFall(Integer expressFall) {
        this.expressFall = expressFall;
    }

    public String getExpressError() {
        return expressError;
    }

    public void setExpressError(String expressError) {
        this.expressError = expressError;
    }

    public Integer getExpressHandle() {
        return expressHandle;
    }

    public void setExpressHandle(Integer expressHandle) {
        this.expressHandle = expressHandle;
    }

    public String getExpressHandleMan() {
        return expressHandleMan;
    }

    public void setExpressHandleMan(String expressHandleMan) {
        this.expressHandleMan = expressHandleMan;
    }

    public Date getExpressHandleDate() {
        return expressHandleDate;
    }

    public void setExpressHandleDate(Date expressHandleDate) {
        this.expressHandleDate = expressHandleDate;
    }

    public String getExpressHandleContent() {
        return expressHandleContent;
    }

    public void setExpressHandleContent(String expressHandleContent) {
        this.expressHandleContent = expressHandleContent;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}

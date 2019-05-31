package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class BurgeonErrorLogSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 单据时间开始
     */
    private Date beginBillDate;
    /**
     * 单据时间结束
     */
    private Date endBillDate;
    /**
     * 伯俊单号
     */
    private String burgeonSn;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 是否处理
     */
    private Integer handle;

    public Date getBeginBillDate() {
        return beginBillDate;
    }

    public void setBeginBillDate(Date beginBillDate) {
        this.beginBillDate = beginBillDate;
    }

    public Date getEndBillDate() {
        return endBillDate;
    }

    public void setEndBillDate(Date endBillDate) {
        this.endBillDate = endBillDate;
    }

    public String getBurgeonSn() {
        return burgeonSn;
    }

    public void setBurgeonSn(String burgeonSn) {
        this.burgeonSn = burgeonSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

}

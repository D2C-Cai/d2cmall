package com.d2c.product.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;

public class UploadProductBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 款号
     */
    private String inernalSn;
    /**
     * 条码
     */
    private String barCode;
    /**
     * 库存
     */
    private Integer store;
    /**
     * 第三方库存
     */
    private Integer popStore;
    /**
     * 销售价
     */
    private BigDecimal price;
    /**
     * 发货说明
     */
    private String remark;
    /**
     * 设计师货号
     */
    private String externalSn;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

}

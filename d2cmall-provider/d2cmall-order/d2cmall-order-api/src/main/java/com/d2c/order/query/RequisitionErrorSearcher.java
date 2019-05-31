package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class RequisitionErrorSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 编号
     */
    private String requisitionSn;
    /**
     * 类型
     */
    private String type;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品条码
     */
    private String barcode;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}

package com.d2c.order.dto;

import java.io.Serializable;

public class RequisitionSummaryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 汇总数量
     */
    private Integer quantity;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

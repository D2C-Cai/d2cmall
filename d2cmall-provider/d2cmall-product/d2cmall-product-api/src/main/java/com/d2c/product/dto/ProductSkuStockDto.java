package com.d2c.product.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ProductSkuStockDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * D2C货号
     */
    private String inernalSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 条码
     */
    private String barCode;
    /**
     * 颜色
     */
    private String sp1;
    /**
     * 尺码
     */
    private String sp2;
    /**
     * 门店
     */
    private String name;
    /**
     * 库存
     */
    private String stock;
    /**
     * 品牌名称
     */
    private String brandName;

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getColorValue() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public void setColorValue() {
    }

    public String getSizeValue() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public void setSizeValue() {
    }

}

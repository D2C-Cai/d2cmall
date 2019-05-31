package com.d2c.order.third.caomei;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class CaomeiProduct {

    /**
     * 商品ID
     */
    private String prodId;
    /**
     * 商品编号
     */
    private String prodNum;
    /**
     * 品牌
     */
    private String prodBrandLangName;
    /**
     * 系列
     */
    private String prodLineLangName;
    /**
     * 商品名称
     */
    private String prodLangName;
    /**
     * 类目
     */
    private String prodCatgName;
    /**
     * 尺码
     */
    private String prodLangSize;
    /**
     * 库存等级
     */
    private Integer invQty;
    /**
     * 销售价
     */
    private BigDecimal sellingPrice;
    /**
     * 建议零售价
     */
    private BigDecimal refPrice;
    /**
     * 货币
     */
    private String currency;
    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 商品描述
     */
    private String photoDescription;
    /**
     * 当前促销
     */
    private String giftAndSpecials;
    /**
     * 商品创建时间
     */
    @JSONField(format = "MM/dd/yyyy")
    private Date prodCreateDate;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getProdBrandLangName() {
        return prodBrandLangName;
    }

    public void setProdBrandLangName(String prodBrandLangName) {
        this.prodBrandLangName = prodBrandLangName;
    }

    public String getProdLineLangName() {
        return prodLineLangName;
    }

    public void setProdLineLangName(String prodLineLangName) {
        this.prodLineLangName = prodLineLangName;
    }

    public String getProdLangName() {
        return prodLangName;
    }

    public void setProdLangName(String prodLangName) {
        this.prodLangName = prodLangName;
    }

    public String getProdCatgName() {
        return prodCatgName;
    }

    public void setProdCatgName(String prodCatgName) {
        this.prodCatgName = prodCatgName;
    }

    public String getProdLangSize() {
        return prodLangSize;
    }

    public void setProdLangSize(String prodLangSize) {
        this.prodLangSize = prodLangSize;
    }

    public Integer getInvQty() {
        return invQty;
    }

    public void setInvQty(Integer invQty) {
        this.invQty = invQty;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(BigDecimal refPrice) {
        this.refPrice = refPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }

    public String getGiftAndSpecials() {
        return giftAndSpecials;
    }

    public void setGiftAndSpecials(String giftAndSpecials) {
        this.giftAndSpecials = giftAndSpecials;
    }

    public Date getProdCreateDate() {
        return prodCreateDate;
    }

    public void setProdCreateDate(Date prodCreateDate) {
        this.prodCreateDate = prodCreateDate;
    }

}

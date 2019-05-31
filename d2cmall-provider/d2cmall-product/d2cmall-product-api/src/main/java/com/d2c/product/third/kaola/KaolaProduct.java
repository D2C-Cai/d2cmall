package com.d2c.product.third.kaola;

import java.io.Serializable;
import java.math.BigDecimal;

public class KaolaProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品款号
     */
    private String productSn;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 考拉价格
     */
    private BigDecimal kaolaPrice;
    /**
     * 上下架
     */
    private Integer mark;
    /**
     * 是否交税
     */
    private Integer taxation = 1;
    /**
     * 是否运费
     */
    private Integer shipping = 1;
    /**
     * 商品库存
     */
    private Integer popStore;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getKaolaPrice() {
        return kaolaPrice;
    }

    public void setKaolaPrice(BigDecimal kaolaPrice) {
        this.kaolaPrice = kaolaPrice;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getTaxation() {
        return taxation;
    }

    public void setTaxation(Integer taxation) {
        this.taxation = taxation;
    }

    public Integer getShipping() {
        return shipping;
    }

    public void setShipping(Integer shipping) {
        this.shipping = shipping;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

}

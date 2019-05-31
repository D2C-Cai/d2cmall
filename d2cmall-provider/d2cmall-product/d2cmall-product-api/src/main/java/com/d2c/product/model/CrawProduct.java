package com.d2c.product.model;

import com.d2c.common.api.model.BaseParentDO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 爬虫商品
 */
@Table(name = "craw_product")
public class CrawProduct extends BaseParentDO<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    protected Long id;
    /**
     * 抓取商品的id
     */
    private Long crawProductId;
    /**
     * 商品url
     */
    private String crawProductUrl;
    /**
     * 商品价格
     */
    private BigDecimal crawProductPrice;
    /**
     * 商品图片url
     */
    private String crawProductPicUrl;
    /**
     * 商品货号
     */
    private String crawProductSn;
    /**
     * 抓取店铺地址
     */
    private Long crawDesignermall;
    /**
     * 商品名称
     */
    private String crawProductName;
    /**
     * D2C商品id
     */
    private Long d2cProductId;
    /**
     * D2C设计师ID
     */
    private Long d2cDesignerId;

    public Long getCrawProductId() {
        return crawProductId;
    }

    public void setCrawProductId(Long crawProductId) {
        this.crawProductId = crawProductId;
    }

    public String getCrawProductUrl() {
        return crawProductUrl;
    }

    public void setCrawProductUrl(String crawProductUrl) {
        this.crawProductUrl = crawProductUrl == null ? null : crawProductUrl.trim();
    }

    public BigDecimal getCrawProductPrice() {
        return crawProductPrice;
    }

    public void setCrawProductPrice(BigDecimal crawProductPrice) {
        this.crawProductPrice = crawProductPrice;
    }

    public String getCrawProductPicUrl() {
        return crawProductPicUrl;
    }

    public void setCrawProductPicUrl(String crawProductPicUrl) {
        this.crawProductPicUrl = crawProductPicUrl == null ? null : crawProductPicUrl.trim();
    }

    public String getCrawProductSn() {
        return crawProductSn;
    }

    public void setCrawProductSn(String crawProductSn) {
        this.crawProductSn = crawProductSn == null ? null : crawProductSn.trim();
    }

    public Long getCrawDesignermall() {
        return crawDesignermall;
    }

    public void setCrawDesignermall(Long crawDesignermall) {
        this.crawDesignermall = crawDesignermall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrawProductName() {
        return crawProductName;
    }

    public void setCrawProductName(String crawProductName) {
        this.crawProductName = crawProductName;
    }

    public Long getD2cProductId() {
        return d2cProductId;
    }

    public void setD2cProductId(Long d2cProductId) {
        this.d2cProductId = d2cProductId;
    }

    public Long getD2cDesignerId() {
        return d2cDesignerId;
    }

    public void setD2cDesignerId(Long d2cDesignerId) {
        this.d2cDesignerId = d2cDesignerId;
    }

}
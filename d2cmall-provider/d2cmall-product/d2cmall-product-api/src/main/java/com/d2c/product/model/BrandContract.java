package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 品牌合同
 *
 * @author Lain
 */
@Table(name = "p_brand_contract")
public class BrandContract extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 合作状态(1合作中 -1终止合作)
     */
    private Integer status;
    /**
     * 入驻合同模式(入驻，代销)
     */
    private String type;
    /**
     * 合同开始时间
     */
    private Date startDate;
    /**
     * 合同结束时间
     */
    private Date endDate;
    /**
     * 平台分成
     */
    private BigDecimal platformRatio;
    /**
     * 品牌分成
     */
    private BigDecimal brandRatio;
    /**
     * 合同文件地址
     */
    private String url;
    /**
     * 唯一(用于互斥合同)
     */
    private Integer uniqueType;
    /**
     * 商品清单文件
     */
    private String goodsUrl;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getPlatformRatio() {
        return platformRatio;
    }

    public void setPlatformRatio(BigDecimal platformRatio) {
        this.platformRatio = platformRatio;
    }

    public BigDecimal getBrandRatio() {
        return brandRatio;
    }

    public void setBrandRatio(BigDecimal brandRatio) {
        this.brandRatio = brandRatio;
    }

    public Integer getUniqueType() {
        return uniqueType;
    }

    public void setUniqueType(Integer uniqueType) {
        this.uniqueType = uniqueType;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public enum SaleType {
        POPPRODUCT("POP代销"), BUYOUT("自营买断"), CONSIGN("自营代销"), SELF("自营自产"), CONSIGNMENT("寄售");
        private String name;

        SaleType(String name) {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

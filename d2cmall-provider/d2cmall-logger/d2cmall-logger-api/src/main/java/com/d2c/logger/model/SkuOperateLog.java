package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * SKU日志
 */
@Table(name = "log_sku")
public class SkuOperateLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 条码ID
     */
    private Long skuId;
    /**
     * skuSn
     */
    private String skuSn;
    /**
     * 商品Sn
     */
    private String productSn;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 旧数据JSON
     */
    private String oldContent;
    /**
     * 新数据JSON
     */
    private String newContent;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

}

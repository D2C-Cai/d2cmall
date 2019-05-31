package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * SKU库存统计
 */
@Table(name = "product_sku_stock_summary")
public class ProductSkuStockSummary extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 条码ID
     */
    @AssertColumn("条码ID不能为空")
    private Long skuId;
    /**
     * 产品ID
     */
    @AssertColumn("产品ID不能为空")
    private Long productId;
    /**
     * 条码
     */
    private String barCode;
    /**
     * 顺丰库存
     */
    private Integer sfStock = 0;
    /**
     * 门店库存
     */
    private Integer stStock = 0;
    /**
     * sku状态 1正常，-1删除
     */
    private Integer status = 1;
    /**
     * 销售属性1 color
     */
    private String sp1;
    /**
     * 销售属性2 size
     */
    private String sp2;

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

    public Integer getSfStock() {
        return sfStock;
    }

    public void setSfStock(Integer sfStock) {
        this.sfStock = sfStock;
    }

    public Integer getStStock() {
        return stStock;
    }

    public void setStStock(Integer stStock) {
        this.stStock = stStock;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

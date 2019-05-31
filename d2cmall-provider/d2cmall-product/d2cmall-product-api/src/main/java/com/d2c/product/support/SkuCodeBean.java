package com.d2c.product.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Map;

public class SkuCodeBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 商品款号
     */
    private String productSn;
    /**
     * SKU条码
     */
    private String barCode;
    /**
     * 设计师ID/名字
     */
    private Map<String, Object> designer;
    /**
     * SKU的颜色/尺码信息
     */
    private Map<String, Map<String, Object>> skuSalesProperties;

    public Map<String, Object> getDesigner() {
        return designer;
    }

    public void setDesigner(Map<String, Object> designer) {
        this.designer = designer;
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

    public Map<String, Map<String, Object>> getSkuSalesProperties() {
        return skuSalesProperties;
    }

    public void setSkuSalesProperties(Map<String, Map<String, Object>> skuSalesProperties) {
        this.skuSalesProperties = skuSalesProperties;
    }

}
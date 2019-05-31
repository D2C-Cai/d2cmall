package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

@Table(name = "p_guanyi_stock")
public class GuanyiStock extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * sku
     */
    private String sku;
    /**
     * 货号
     */
    private String productSn;
    /**
     * 销售属性（颜色 尺码）
     */
    private String saleCategory;
    /**
     * 商品名称（和官网不一致）
     */
    private String productName;
    /**
     * 仓库
     */
    private String store;
    /**
     * 可售数量
     */
    private Integer salableQty;
    /**
     * 在途数量
     */
    private Integer roadQty;
    /**
     * 可配数
     */
    private Integer pickQty;
    /**
     * 库存数量
     */
    private Integer qty;

    public GuanyiStock() {
    }

    public GuanyiStock(JSONObject obj) {
        this.sku = obj.getString("sku_code");
        this.productSn = obj.getString("item_code");
        this.saleCategory = obj.getString("item_sku_name");
        this.productName = obj.getString("item_name");
        this.store = obj.getString("warehouse_name");
        this.salableQty = obj.getInteger("salable_qty");
        this.roadQty = obj.getInteger("road_qty");
        this.pickQty = obj.getInteger("pick_qty");
        this.qty = obj.getInteger("qty");
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Integer getSalableQty() {
        return salableQty;
    }

    public void setSalableQty(Integer salableQty) {
        this.salableQty = salableQty;
    }

    public String getSaleCategory() {
        return saleCategory;
    }

    public void setSaleCategory(String saleCategory) {
        this.saleCategory = saleCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getRoadQty() {
        return roadQty;
    }

    public void setRoadQty(Integer roadQty) {
        this.roadQty = roadQty;
    }

    public Integer getPickQty() {
        return pickQty;
    }

    public void setPickQty(Integer pickQty) {
        this.pickQty = pickQty;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

}

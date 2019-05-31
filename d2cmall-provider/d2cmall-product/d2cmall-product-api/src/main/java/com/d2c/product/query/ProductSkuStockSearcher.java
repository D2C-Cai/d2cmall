package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;

import java.util.List;

public class ProductSkuStockSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * SKU条码
     */
    private String barCode;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品款号
     */
    private String inernalSn;
    /**
     * 外部货号
     */
    private String externalSn;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师IDs
     */
    private List<Long> designerIds;
    /**
     * 设计师名字
     */
    private String designers;
    /**
     * 设计师品牌
     */
    private String designerName;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 区分门店和设计师
     */
    private String type;
    /**
     * 年度
     */
    private String year;
    /**
     * 季节
     */
    private String season;
    /**
     * 0无库存，1有库存
     */
    private Integer stocked;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getStocked() {
        return stocked;
    }

    public void setStocked(Integer stocked) {
        this.stocked = stocked;
    }

    @Override
    public void setStoreId(Long storeId) {
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designerIds = brandIds;
    }

}

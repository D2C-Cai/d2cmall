package com.d2c.order.third.kaola.reponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class GoodsInfo {

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 供货价
     */
    private BigDecimal price;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 建议零售价
     */
    private BigDecimal suggestPrice;
    /**
     * 整个考拉的库存
     */
    private Integer store;
    /**
     * 推荐仓库，供展示已经下单用
     */
    private WarehouseStore recommandStore;
    /**
     * 所有该商品仓库库存 TODO
     */
    private Map<String, WarehouseStore> warehouseStores;
    /**
     * 0:下架不可卖，1：上架可卖
     */
    private Integer onlineStatus;
    /**
     * 忽略（商品ID）
     */
    private Long productId;
    /**
     * 税费-只有在channelSalePrice有值的情况下才返回
     */
    private BigDecimal taxFees;
    /**
     * 商品价格+对应的税费 -只有在channelSalePrice有值的情况下才返回
     */
    private BigDecimal afterTaxPrice;
    /**
     * 0 不包税，1包税
     */
    private Integer isFreeTax;
    /**
     * 0:非预售，1:预售
     */
    private Integer isPresell;
    /**
     * 0:不包邮，1:包邮
     */
    private Integer isFreeShipping;
    /**
     * 商品件数
     */
    private Integer memberCount;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(BigDecimal suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public WarehouseStore getRecommandStore() {
        return recommandStore;
    }

    public void setRecommandStore(WarehouseStore recommandStore) {
        this.recommandStore = recommandStore;
    }

    public Map<String, WarehouseStore> getWarehouseStores() {
        return warehouseStores;
    }

    public void setWarehouseStores(Map<String, WarehouseStore> warehouseStores) {
        this.warehouseStores = warehouseStores;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getTaxFees() {
        return taxFees;
    }

    public void setTaxFees(BigDecimal taxFees) {
        this.taxFees = taxFees;
    }

    public BigDecimal getAfterTaxPrice() {
        return afterTaxPrice;
    }

    public void setAfterTaxPrice(BigDecimal afterTaxPrice) {
        this.afterTaxPrice = afterTaxPrice;
    }

    public Integer getIsFreeTax() {
        return isFreeTax;
    }

    public void setIsFreeTax(Integer isFreeTax) {
        this.isFreeTax = isFreeTax;
    }

    public Integer getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(Integer isPresell) {
        this.isPresell = isPresell;
    }

    public Integer getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Set<String> getWarehouseStoreName() {
        if (warehouseStores != null) {
            return warehouseStores.keySet();
        }
        return null;
    }

}
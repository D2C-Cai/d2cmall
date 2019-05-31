package com.d2c.product.dto;

import com.d2c.product.model.ProductSku;

public class ProductSkuDto extends ProductSku {

    private static final long serialVersionUID = 1L;
    /**
     * 订单满减活动
     */
    private Long orderPromotionId;
    /**
     * 组合促销策略
     */
    private Long combinationPromotionId;
    /**
     * 门店库存
     */
    private Integer storeStock = 0;
    /**
     * 占单库存
     */
    private Integer occupyStock = 0;

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public Long getCombinationPromotionId() {
        return combinationPromotionId;
    }

    public void setCombinationPromotionId(Long combinationPromotionId) {
        this.combinationPromotionId = combinationPromotionId;
    }

    public Integer getStoreStock() {
        return storeStock;
    }

    public void setStoreStock(Integer storeStock) {
        this.storeStock = storeStock;
    }

    public Integer getOccupyStock() {
        return occupyStock;
    }

    public void setOccupyStock(Integer occupyStock) {
        this.occupyStock = occupyStock;
    }

}

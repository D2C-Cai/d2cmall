package com.d2c.similar.dto;

import com.d2c.common.api.dto.BaseDTO;

public class ProductRecomDTO extends BaseDTO {

    private static final long serialVersionUID = -8262392077922328853L;
    private Long productId;
    //当月销量
    private Integer sales;
    //当月销量
    private Integer recentlySales;
    //当月点击量
    private Integer hits;
    //评论数量
    private Integer comments;
    //新品推荐，上架到现在的天数
    private Integer newDays;
    //运营推荐
    private Boolean operRecom = false;
    //活动推荐
    private Boolean promotion = false;
    //商品在所有购物车中的数量
    private Integer shoppingNum;
    //商品在所有收藏夹中的数量
    private Integer collectNum;

    public ProductRecomDTO() {
    }

    public ProductRecomDTO(Long id) {
        productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getRecentlySales() {
        return recentlySales;
    }

    public void setRecentlySales(Integer recentlySales) {
        this.recentlySales = recentlySales;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getNewDays() {
        return newDays;
    }

    public void setNewDays(Integer newDays) {
        this.newDays = newDays;
    }

    public Boolean getOperRecom() {
        return operRecom;
    }

    public void setOperRecom(Boolean operRecom) {
        this.operRecom = operRecom;
    }

    public Boolean getPromotion() {
        return promotion;
    }

    public void setPromotion(Boolean promotion) {
        this.promotion = promotion;
    }

    public Integer getShoppingNum() {
        return shoppingNum;
    }

    public void setShoppingNum(Integer shoppingNum) {
        this.shoppingNum = shoppingNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

}

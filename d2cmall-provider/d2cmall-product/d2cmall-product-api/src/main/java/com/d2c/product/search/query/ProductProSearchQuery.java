package com.d2c.product.search.query;

import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductProSearchQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 排列顺序
     */
    private String orderByStr;
    /**
     * 商品货号
     */
    private String[] inernalSn;
    /**
     * 销售类型
     */
    private List<String> productSellType;
    /**
     * 全球购
     */
    private Boolean worldTrade;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 系列ID
     */
    private Long seriesId;
    /**
     * 一级分类ID
     */
    private Long topId;
    /**
     * 二级分类ID
     */
    private Long categoryId;
    /**
     * 直播间ID
     */
    private Long liveId;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 商品ID
     */
    private List<String> productIds;
    /**
     * 设计师ID
     */
    private List<Long> designerIds;
    /**
     * 系列ID
     */
    private List<Long> seriesIds;
    /**
     * 分类ID
     */
    private List<Long> categoryIds;
    /**
     * 一级分类ID
     */
    private Long[] topIds;
    /**
     * 排除的商品ID
     */
    private Long[] ignoreIds;
    /**
     * 活动ID
     */
    private Long promotionId;
    /**
     * 活动ID
     */
    private Long orderPromotionId;
    /**
     * 限时购ID
     */
    private Long flashPromotionId;
    /**
     * 排序字段
     */
    private String[] sortFields;
    /**
     * 顺序
     */
    private SortOrder[] orders;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 存在库存 （1有库存，0无库存）
     */
    private Integer store;
    /**
     * 是否推荐
     */
    private Integer recommend;
    /**
     * 是否置顶
     */
    private Integer topical;
    /**
     * 是否支持售后
     */
    private Boolean after;
    /**
     * 是否门店试衣
     */
    private Integer subscribe;
    /**
     * 是否推荐（1是，0否）
     */
    private Integer operRecom;
    /**
     * 是否有活动
     */
    private Boolean hasPromotion;
    /**
     * 销量统计月份
     */
    private Integer month;
    /**
     * 修改时间
     */
    private Date afterModifyDate;
    /**
     * 创建时间
     */
    private Date beginCreateDate;
    /**
     * 活动时间
     */
    private Date promotionDate;
    /**
     * 上架时间区间
     */
    private Date beginUpDate;
    private Date endUpDate;
    /**
     * 价格区间
     */
    private Integer minPrice;
    private Integer maxPrice;
    /**
     * 商品推荐值区间
     */
    private Double minRecom;
    private Double maxRecom;
    /**
     * 是否上架
     */
    private Boolean mark;
    /**
     * 是否可搜索
     */
    private Boolean search;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        if (keyword != null) {
            keyword = keyword.toLowerCase();
        }
        this.keyword = keyword;
    }

    public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

    public String[] getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String[] inernalSn) {
        this.inernalSn = inernalSn;
    }

    public List<String> getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(List<String> productSellType) {
        this.productSellType = productSellType;
    }

    public Boolean getWorldTrade() {
        return worldTrade;
    }

    public void setWorldTrade(Boolean worldTrade) {
        this.worldTrade = worldTrade;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    public List<Long> getSeriesIds() {
        return seriesIds;
    }

    public void setSeriesIds(List<Long> seriesIds) {
        this.seriesIds = seriesIds;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long[] getTopIds() {
        return topIds;
    }

    public void setTopIds(Long[] topIds) {
        this.topIds = topIds;
    }

    public Long[] getIgnoreIds() {
        return ignoreIds;
    }

    public void setIgnoreIds(Long[] ignoreIds) {
        this.ignoreIds = ignoreIds;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public String[] getSortFields() {
        return sortFields;
    }

    public void setSortFields(String[] sortFields) {
        this.sortFields = sortFields;
    }

    public SortOrder[] getOrders() {
        return orders;
    }

    public void setOrders(SortOrder[] orders) {
        this.orders = orders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getTopical() {
        return topical;
    }

    public void setTopical(Integer topical) {
        this.topical = topical;
    }

    public Boolean getAfter() {
        return after;
    }

    public void setAfter(Boolean after) {
        this.after = after;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getOperRecom() {
        return operRecom;
    }

    public void setOperRecom(Integer operRecom) {
        this.operRecom = operRecom;
    }

    public Boolean getHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(Boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Date getAfterModifyDate() {
        return afterModifyDate;
    }

    public void setAfterModifyDate(Date afterModifyDate) {
        this.afterModifyDate = afterModifyDate;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(Date promotionDate) {
        this.promotionDate = promotionDate;
    }

    public Date getBeginUpDate() {
        return beginUpDate;
    }

    public void setBeginUpDate(Date beginUpDate) {
        this.beginUpDate = beginUpDate;
    }

    public Date getEndUpDate() {
        return endUpDate;
    }

    public void setEndUpDate(Date endUpDate) {
        this.endUpDate = endUpDate;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinRecom() {
        return minRecom;
    }

    public void setMinRecom(Double minRecom) {
        this.minRecom = minRecom;
    }

    public Double getMaxRecom() {
        return maxRecom;
    }

    public void setMaxRecom(Double maxRecom) {
        this.maxRecom = maxRecom;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    public Boolean getSearch() {
        return search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    public Integer getMarkInt() {
        if (mark == null) {
            return 1;
        }
        return mark ? 1 : 0;
    }

    public Integer getSearchInt() {
        if (search == null) {
            return 1;
        }
        return search ? 1 : 0;
    }

}

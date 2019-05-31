package com.d2c.product.query.search;

import com.d2c.common.api.annotation.constant.SearchParamConst;
import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.annotation.search.SearchIgnore;
import com.d2c.common.api.annotation.search.SearchParam;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.model.EsQuery;

import java.util.Date;
import java.util.List;

public class ProductSearchQuery extends EsQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    @SearchField(oper = OperType.MULTI_MATCH, params = {
            @SearchParam(name = SearchParamConst.MULTI_MATCH_FIELDS, values = {"name^8", "designer^5", "brand^5", "seo^3", "categoryName^3",
                    "inernalSn^3", "promotionName^3", "subTitle^1", "externalSn^1", "topCategory^1", "productCategory^1"})
    })
    private String keyword;
    /**
     * 排序字段 ProductOrderEnum
     */
    @SearchIgnore
    private String order;
    /**
     * 商品状态标志
     */
    private Integer mark;
    /**
     * 是否可搜索
     */
    private Integer search;
    /**
     * 活动ID
     */
    private Long promotionId;
    /**
     * 是否有活动
     */
    private Boolean hasPromotion;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师ID
     */
    private List<Long> designerIds;
    /**
     * 系列ID
     */
    private Long seriesId;
    /**
     * 二级分类ID
     */
    private Long categoryId;
    /**
     * 一级分类ID
     */
    @SearchField("topCategoryId")
    private Long topId;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 出库 （1有库存，0无库存，null 不限制）
     */
    private Integer store;
    /**
     * 销量统计月份
     */
    private Integer month;
    /**
     * 排除的商品id
     */
    private Long[] ignoreIds;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 价格区间 最低价
     */
    @SearchField(name = "promotionPrice", oper = OperType.GTE)
    private Integer minPrice;
    /**
     * 价格区间 最高价
     */
    @SearchField(name = "promotionPrice", oper = OperType.LTE)
    private Integer maxPrice;
    /**
     * 商品推荐值区间 最低推荐
     */
    @SearchField(name = "recomScore", oper = OperType.GTE)
    private Double minRecom;
    /**
     * 商品推荐值区间 最高推荐
     */
    @SearchField(name = "recomScore", oper = OperType.LTE)
    private Double maxRecom;
    /**
     * 是否运营推荐商品 1:是 0:否
     */
    private Integer operRecom;
    /**
     * 直播间ID
     */
    private Long liveId;
    /**
     * 商品id
     */
    private List<String> productIds;
    /**
     * 货号
     */
    private String[] inernalSn;
    /**
     * 推荐
     */
    private Integer recommend;
    /**
     * 是否置顶
     */
    private Integer topical;
    /**
     * 上架时间
     */
    @SearchField(name = "upMarketDate", oper = OperType.GTE)
    private Date beginUpDate;
    @SearchField(name = "upMarketDate", oper = OperType.LTE)
    private Date endUpDate;
    /**
     * 修改日期在此之后
     */
    @SearchField(name = "modifyDate", oper = OperType.GTE)
    private Date afterModifyDate;
    /**
     * 分类
     */
    private List<Long> categoryIds;
    /**
     * 是否支持售后
     */
    private Boolean after;
    /**
     * 是否门店试衣
     */
    private Integer subscribe;
    /**
     * 销售类型
     */
    private List<String> productSellType;
    /**
     * 活动时间
     */
    private Date promotionDate;

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getSearch() {
        return search;
    }

    public void setSearch(Integer search) {
        this.search = search;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        if (keyword != null) {
            keyword = keyword.toLowerCase();
        }
        this.keyword = keyword;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long[] getIgnoreIds() {
        return ignoreIds;
    }

    public void setIgnoreIds(Long[] ignoreIds) {
        this.ignoreIds = ignoreIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
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

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Date getAfterModifyDate() {
        return afterModifyDate;
    }

    public void setAfterModifyDate(Date afterModifyDate) {
        this.afterModifyDate = afterModifyDate;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
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

    public List<String> getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(List<String> productSellType) {
        this.productSellType = productSellType;
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

    public String[] getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String[] inernalSn) {
        this.inernalSn = inernalSn;
    }

    public Date getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(Date promotionDate) {
        this.promotionDate = promotionDate;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getO() {
        return order;
    }

    public void setO(String orderByStr) {
        this.order = orderByStr;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}

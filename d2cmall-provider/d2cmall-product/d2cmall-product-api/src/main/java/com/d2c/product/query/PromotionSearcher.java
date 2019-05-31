package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PromotionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 活动ID
     */
    private Long id;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 是否全场
     */
    private Boolean whole;
    /**
     * 促销类型
     */
    private Integer promotionType;
    /**
     * 促销范围
     */
    private Integer promotionScope;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 开始的开始时间
     */
    private Date beginStartTime;
    /**
     * 开始的结束时间
     */
    private Date endStartTime;
    /**
     * 开始的结束时间
     */
    private Date beginEndTime;
    /**
     * 结束的结束时间
     */
    private Date endEndTime;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 定时上架
     */
    private Integer timing;
    /**
     * 关联的品牌ID
     */
    private Long brandId;
    /**
     * 是否绑定品牌，且上传了品牌图片
     */
    private Boolean bindBrand;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getWhole() {
        return whole;
    }

    public void setWhole(Boolean whole) {
        this.whole = whole;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getPromotionScope() {
        return promotionScope;
    }

    public void setPromotionScope(Integer promotionScope) {
        this.promotionScope = promotionScope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginEndTime() {
        return beginEndTime;
    }

    public void setBeginEndTime(Date beginEndTime) {
        this.beginEndTime = beginEndTime;
    }

    public Date getEndEndTime() {
        return endEndTime;
    }

    public void setEndEndTime(Date endEndTime) {
        this.endEndTime = endEndTime;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Date getBeginStartTime() {
        return beginStartTime;
    }

    public void setBeginStartTime(Date beginStartTime) {
        this.beginStartTime = beginStartTime;
    }

    public Date getEndStartTime() {
        return endStartTime;
    }

    public void setEndStartTime(Date endStartTime) {
        this.endStartTime = endStartTime;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Boolean getBindBrand() {
        return bindBrand;
    }

    public void setBindBrand(Boolean bindBrand) {
        this.bindBrand = bindBrand;
    }

}

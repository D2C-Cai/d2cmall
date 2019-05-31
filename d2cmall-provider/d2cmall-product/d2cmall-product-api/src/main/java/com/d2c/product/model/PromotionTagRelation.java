package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 活动标签关系
 */
@Table(name = "p_promotion_tag_relation")
public class PromotionTagRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 活动ID
     */
    private Long promotionId;
    /**
     * 排序号
     */
    private Integer sort = 0;

    public PromotionTagRelation() {
    }

    public PromotionTagRelation(Long promotionId, Long tagId) {
        this.promotionId = promotionId;
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}

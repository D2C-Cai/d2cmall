package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class BargainPromotionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动状态
     */
    private Integer promotionStatus;
    /**
     * 上下架
     */
    private Integer mark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(Integer promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}

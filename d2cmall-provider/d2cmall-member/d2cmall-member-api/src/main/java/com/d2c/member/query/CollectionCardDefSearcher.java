package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CollectionCardDefSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 活动ID
     */
    private Long promotionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

}

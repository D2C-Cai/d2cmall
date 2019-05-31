package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class CouponDefRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private Long targetId;
    private Boolean exclude = false;
    private String relationType;
    private Long couponDefId;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Long getCouponDefId() {
        return couponDefId;
    }

    public void setCouponDefId(Long couponDefId) {
        this.couponDefId = couponDefId;
    }

}

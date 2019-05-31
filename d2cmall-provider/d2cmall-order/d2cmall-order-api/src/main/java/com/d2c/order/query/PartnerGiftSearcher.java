package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class PartnerGiftSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 买手ID
     */
    private Long partnerId;
    /**
     * DMID
     */
    private Long parentId;
    /**
     * AMID
     */
    private Long masterId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

}

package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class ShareRedPacketsSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 创建时间开始
     */
    private Date beginCreateDate;
    /**
     * 创建时间结束
     */
    private Date endCreateDate;
    /**
     * 活动ID
     */
    private Long promotionId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 组ID
     */
    private Long groupId;
    /**
     * 是否团长
     */
    private Integer initiator;
    /**
     * 状态
     */
    private Integer status;

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

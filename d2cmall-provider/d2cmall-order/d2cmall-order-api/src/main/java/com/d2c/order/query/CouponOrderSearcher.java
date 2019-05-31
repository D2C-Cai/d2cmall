package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class CouponOrderSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 优惠券模板ID
     */
    private Long couponDefId;
    /**
     * 买家ID
     */
    private Long memberId;
    /**
     * 买家账号
     */
    private String loginCode;
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    /**
     * 状态
     */
    private Integer orderStatus;

    public Long getCouponDefId() {
        return couponDefId;
    }

    public void setCouponDefId(Long couponDefId) {
        this.couponDefId = couponDefId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

}

package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 直播间
 */
@Table(name = "m_live_room")
public class LiveRoom extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 绑定设计师的ID
     */
    private Long designersId;
    /**
     * 绑定优惠券的ID
     */
    private Long couponId;
    /**
     * 绑定优惠券组的ID
     */
    private Long couponGroupId;

    public LiveRoom() {
    }

    public LiveRoom(Long memberId, String loginCode, Long designersId) {
        this.memberId = memberId;
        this.loginCode = loginCode;
        this.designersId = designersId;
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

    public Long getDesignersId() {
        return designersId;
    }

    public void setDesignersId(Long designersId) {
        this.designersId = designersId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponGroupId() {
        return couponGroupId;
    }

    public void setCouponGroupId(Long couponGroupId) {
        this.couponGroupId = couponGroupId;
    }

}

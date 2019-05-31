package com.d2c.order.dto;

import com.d2c.member.model.MemberInfo;
import com.d2c.order.model.Coupon;

public class CouponDto extends Coupon {

    private static final long serialVersionUID = 1L;
    /**
     * 会员
     */
    private MemberInfo member;

    public MemberInfo getMember() {
        return member;
    }

    public void setMember(MemberInfo member) {
        this.member = member;
    }

}

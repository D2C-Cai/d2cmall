package com.d2c.member.dto;

import com.d2c.member.model.MemberAttention;
import com.d2c.member.model.MemberInfo;

public class MemberAttentionDto extends MemberAttention {

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

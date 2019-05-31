package com.d2c.member.dto;

import com.d2c.member.model.MemberCollection;
import com.d2c.member.model.MemberInfo;

public class MemberCollectionDto extends MemberCollection {

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

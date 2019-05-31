package com.d2c.member.dto;

import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberTagRelation;

public class MemberTagRelationDto extends MemberTagRelation {

    private static final long serialVersionUID = 1L;
    /**
     * 会员
     */
    private MemberInfo memberInfo;

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

}

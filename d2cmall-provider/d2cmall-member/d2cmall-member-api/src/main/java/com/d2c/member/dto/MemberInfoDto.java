package com.d2c.member.dto;

import com.d2c.member.model.MemberInfo;

public class MemberInfoDto extends MemberInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 登录票据
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

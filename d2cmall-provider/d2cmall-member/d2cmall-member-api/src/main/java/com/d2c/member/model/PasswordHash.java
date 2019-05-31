package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 验证码校验
 */
@Table(name = "m_passwordhash")
public class PasswordHash extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 校验码
     */
    private String hash;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 有效
     */
    private Integer valid;
    /**
     * 登陆ID
     */
    private String loginCode;

    public PasswordHash() {
        this.valid = 1;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

}

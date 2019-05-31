package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class LiveRoomSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 房间号
     */
    private Long id;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 设计师ID
     */
    private String designersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDesignersId() {
        return designersId;
    }

    public void setDesignersId(String designersId) {
        this.designersId = designersId;
    }

}

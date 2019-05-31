package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MemberTagRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 标签IDs
     */
    private Long[] tagIds;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

}

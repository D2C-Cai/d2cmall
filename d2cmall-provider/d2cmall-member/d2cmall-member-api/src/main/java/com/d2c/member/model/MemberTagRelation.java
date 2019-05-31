package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员标签关系
 */
@Table(name = "m_member_tag_relation")
public class MemberTagRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    @AssertColumn("标签ID不能为空")
    private Long tagId;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 上下架 1上架 0下架
     */
    private Integer status = 1;
    /**
     * 标签名称
     */
    private String tagName;

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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}

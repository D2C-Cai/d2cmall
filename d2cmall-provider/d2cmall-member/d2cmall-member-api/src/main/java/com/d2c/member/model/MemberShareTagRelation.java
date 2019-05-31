package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 买家秀标签关系
 */
@Table(name = "m_share_tag_relation")
public class MemberShareTagRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    @AssertColumn("标签ID不能为空")
    private Long tagId;
    /**
     * 买家秀ID
     */
    @AssertColumn("买家秀ID不能为空")
    private Long shareId;
    /**
     * 排序号
     */
    private Integer sort = 0;
    /**
     * 置顶
     */
    private Integer top = 0;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

}

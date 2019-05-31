package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MemberShareTagRelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 买家秀的ID
     */
    private Long shareId;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 置顶
     */
    private Integer top;

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

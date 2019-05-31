package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员标签
 */
@Table(name = "m_member_tag")
public class MemberTag extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签名称
     */
    @AssertColumn("标签名称不能为空")
    private String name;
    /**
     * 是否是固定标签，固定标签不能删除
     */
    private Integer fixed = 0;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 上下架 1上架 0下架
     */
    private Integer status = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

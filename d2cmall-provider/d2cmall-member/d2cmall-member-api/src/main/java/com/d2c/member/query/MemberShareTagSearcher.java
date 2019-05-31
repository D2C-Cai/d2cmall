package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MemberShareTagSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 是否是固定标签，固定标签不能删除
     */
    private Integer fixed;
    /**
     * 排序
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}

package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 买家秀标签
 */
@Table(name = "m_share_tag")
public class MemberShareTag extends PreUserDO {

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
    private Integer status = 0;
    /**
     * 标签代码
     */
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("sort", this.getSort());
        return obj;
    }

}

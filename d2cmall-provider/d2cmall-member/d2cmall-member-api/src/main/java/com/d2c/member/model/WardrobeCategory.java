package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 衣橱分类
 *
 * @author Lain
 */
@Table(name = "m_wardrobe_category")
public class WardrobeCategory extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 顶级名称
     */
    private String topName;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 状态
     */
    private Integer status = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
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

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("topName", this.getTopName());
        return obj;
    }

}

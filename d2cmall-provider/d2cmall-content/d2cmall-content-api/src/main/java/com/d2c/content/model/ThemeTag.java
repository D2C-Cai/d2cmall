package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -专题标签
 */
@Table(name = "v_theme_tag")
public class ThemeTag extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 图标
     */
    private String pic;
    /**
     * 是否固定
     */
    private Integer fix = 0;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否删除
     */
    private Integer deleted = 0;
    /**
     * 类型
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("pic", this.getPic());
        json.put("fix", this.getFix());
        return json;
    }

}

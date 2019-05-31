package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 杂志模板
 */
@Table(name = "m_magazine")
public class Magazine extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String pic;
    /**
     * 状态 0停用 1启用
     */
    private Integer status;
    /**
     * 封面图
     */
    private String coverPic;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("magazineId", this.getId());
        obj.put("magazineName", this.getName());
        obj.put("coverPic", this.getCoverPic());
        return obj;
    }

}

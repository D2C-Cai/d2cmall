package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -pc wap模块内容
 */
@Table(name = "v_field_content")
public class FieldContent extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面定义ID
     */
    private Long modulePageId;
    /**
     * 组名称
     */
    private String groupName;
    /**
     * 标题
     */
    private String title;
    /**
     * 价格
     */
    private String price;
    /**
     * 绑定地址
     */
    private String bindUrl;
    /**
     * 首图
     */
    private String frontPic;
    /**
     * 中间图
     */
    private String midPic;
    /**
     * 背面图
     */
    private String endPic;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 边距 0,0,0,0
     */
    private String margin;
    /**
     * 背景颜色 TODO
     */
    @javax.persistence.Transient
    private String backColor;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBindUrl() {
        return bindUrl;
    }

    public void setBindUrl(String bindUrl) {
        this.bindUrl = bindUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getModulePageId() {
        return modulePageId;
    }

    public void setModulePageId(Long modulePageId) {
        this.modulePageId = modulePageId;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getMidPic() {
        return midPic;
    }

    public void setMidPic(String midPic) {
        this.midPic = midPic;
    }

    public String getEndPic() {
        return endPic;
    }

    public void setEndPic(String endPic) {
        this.endPic = endPic;
    }

    public String getBindUrlId() {
        String url = this.bindUrl;
        int idx = url.lastIndexOf("/");
        String id = url.substring(idx + 1, url.length());
        int idx2 = id.lastIndexOf("?");
        if (idx2 != 0) {
            id = id.substring(idx2 + 1, id.length());
        }
        return id;
    }

    public void setBindUrlId() {
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("title", getTitle());
        obj.put("bindUrl", getBindUrl());
        obj.put("price", getPrice());
        obj.put("frontPicPath", this.getFrontPic() == null ? "" : this.getFrontPic());
        obj.put("middlePicPath", this.getMidPic() == null ? "" : this.getMidPic());
        obj.put("endPicPath", this.getEndPic() == null ? "" : this.getEndPic());
        return obj;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

}

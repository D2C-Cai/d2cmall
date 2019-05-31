package com.d2c.content.search.support;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ThemeTagSearchHelp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 图片
     */
    private String pic;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tagId", this.getTagId());
        json.put("tagName", this.getTagName());
        json.put("pic", this.getPic());
        json.put("count", this.getCount());
        return json;
    }

}

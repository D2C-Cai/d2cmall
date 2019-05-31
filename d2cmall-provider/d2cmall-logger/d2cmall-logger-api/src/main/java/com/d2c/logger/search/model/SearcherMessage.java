package com.d2c.logger.search.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

public class SearcherMessage extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 分类
     */
    private Integer type = 0;
    /**
     * 类型
     */
    private String typeName;
    /**
     * 0:未读 1：已读,-1 删除
     */
    private Integer status = 0;
    /**
     * 接收者ID
     */
    private Long recId;
    /**
     * URL
     */
    private String url;
    /**
     * 是否全站
     */
    private Integer global = 0;
    /**
     * 消息图片
     */
    private String pic;
    /**
     * 消息的其他内容
     */
    private String other;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getGlobal() {
        return global;
    }

    public void setGlobal(Integer global) {
        this.global = global;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    private JSONObject getOtherJson() {
        JSONObject obj = null;
        if (this.getOther() != null) {
            obj = JSON.parseObject(this.getOther());
        }
        return obj;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("type", this.getType());
        obj.put("typeName", this.getTypeName());
        obj.put("title", this.getTitle());
        obj.put("content", this.getContent());
        obj.put("status", this.getStatus());
        obj.put("recId", this.getRecId());
        if (StringUtils.isNotBlank(this.getUrl())) {
            obj.put("url", this.getUrl());
        } else {
            obj.put("url", "/message/" + this.getId());
        }
        obj.put("global", this.getGlobal() != null ? this.getGlobal() : 0);
        obj.put("pic", this.getPic());
        obj.put("timestamp", this.getCreateDate().getTime());
        obj.put("other", getOtherJson());
        return obj;
    }

}

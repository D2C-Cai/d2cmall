package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;

import java.util.Date;

public class SearcherTopic extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String pic;
    /**
     * 发布时间
     */
    private Date upMarketDate;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 置顶
     */
    private Integer top;
    /**
     * 说明
     */
    private String content;
    /**
     * 状态
     */
    private Integer status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("title", this.getTitle());
        json.put("content", this.getContent());
        json.put("pic", this.getPic());
        return json;
    }

}

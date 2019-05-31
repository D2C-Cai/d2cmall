package com.d2c.content.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class SearcherTheme extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * url
     */
    private String url;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 封面图
     */
    private String pic;
    /**
     * 推荐-本周精选
     */
    private Integer recommend;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * WAP内容
     */
    private String wapContent;
    /**
     * 是否固定
     */
    private Integer fix;
    /**
     * 页面描述
     */
    private String metaDescription;
    /**
     * 时间
     */
    private Date beginDate;
    private Date endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWapContent() {
        return wapContent;
    }

    public void setWapContent(String wapContent) {
        this.wapContent = wapContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("createDate", this.getCreateDate());
        json.put("pic", this.getPic());
        json.put("title", this.getTitle());
        json.put("url", this.getWapUrl());
        json.put("recommend", this.getRecommend());
        json.put("tagId", this.getTagId());
        json.put("tagName", this.getTagName());
        return json;
    }

    private String getWapUrl() {
        if (StringUtils.isNotBlank(this.getUrl())) {
            return this.getUrl();
        }
        if (StringUtils.isNotBlank(this.getWapContent())) {
            return "/theme/" + this.getId();
        }
        return null;
    }

}

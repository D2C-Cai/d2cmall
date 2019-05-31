package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -文章
 */
@Table(name = "v_article")
public class Article extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 中文标题
     */
    @AssertColumn("文章标题不能为空")
    private String title;
    /**
     * 英文标题
     */
    @AssertColumn("英文标题不能为空")
    private String name;
    /**
     * 文章图片
     */
    private String picture;
    /**
     * 文章作者
     */
    private String author;
    /**
     * 分享内容
     */
    private String shareContent;
    /**
     * PC版内容
     */
    private String content;
    /**
     * 手机版内容
     */
    private String mobileContent;
    /**
     * 发布日期
     */
    private Date publishDate;
    /**
     * 页面关键词
     */
    private String metaKeywords;
    /**
     * 页面描述
     */
    private String metaDescription;
    /**
     * 是否发布
     */
    private boolean published = false;
    /**
     * 是否置顶
     */
    private boolean toped = false;
    /**
     * 是否推荐
     */
    private boolean recommend = false;
    /**
     * 点击数
     */
    private Integer hits = 0;
    /**
     * 文章页数
     */
    private Integer pageCount = 0;
    /**
     * html路径
     */
    private String htmlFilePath;
    /**
     * 生成网页
     */
    private String html;
    /**
     * 文章分类ID
     */
    private Long categoryId;
    /**
     * 页面模板ID
     */
    private Long articleTemplateId;
    /**
     * 二维码分享的背景图
     */
    private String backgroundUrl;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 品牌页展示图片
     */
    private String brandPic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public boolean isToped() {
        return toped;
    }

    public void setToped(boolean toped) {
        this.toped = toped;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getHtmlFilePath() {
        return htmlFilePath;
    }

    public void setHtmlFilePath(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(String mobileContent) {
        this.mobileContent = mobileContent;
    }

    public Long getArticleTemplateId() {
        return articleTemplateId;
    }

    public void setArticleTemplateId(Long articleTemplateId) {
        this.articleTemplateId = articleTemplateId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("title", getTitle());
        obj.put("name", this.getName());
        obj.put("picture", this.getPicture());
        obj.put("publishDate", this.getPublishDate());
        obj.put("hits", this.getHits());
        obj.put("articleTemplateId", this.getArticleTemplateId());
        String content = this.getMobileContent();
        if (StringUtils.isNotBlank(this.getMobileContent())) {
            content = this.getMobileContent();
        } else {
            content = this.getContent();
        }
        content = StringUtil.replaceHref(content);
        obj.put("content", content);
        obj.put("backgroundUrl", this.getBackgroundUrl());
        obj.put("brandPic", this.getBrandPic());
        return obj;
    }

}
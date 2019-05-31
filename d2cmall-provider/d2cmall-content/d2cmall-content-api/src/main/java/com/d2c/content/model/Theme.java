package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 专题
 *
 * @author Lain
 */
@Table(name = "v_theme")
public class Theme extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 专题THEME 微商WECHAT
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
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否删除
     */
    private Integer deleted = 0;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * PC内容
     */
    private String pcContent;
    /**
     * WAP内容
     */
    private String wapContent;
    /**
     * 分享图片
     */
    private String sharePic;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 页面描述
     */
    private String metaDescription;
    /**
     * 时间
     */
    private Date beginDate;
    private Date endDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getPcContent() {
        return pcContent;
    }

    public void setPcContent(String pcContent) {
        this.pcContent = pcContent;
    }

    public String getWapContent() {
        return wapContent;
    }

    public void setWapContent(String wapContent) {
        this.wapContent = wapContent;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public enum ThemeType {
        THEME, WECHAT
    }

}

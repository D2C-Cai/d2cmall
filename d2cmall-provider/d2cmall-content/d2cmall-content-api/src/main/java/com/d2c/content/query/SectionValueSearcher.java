package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;
import java.util.List;

public class SectionValueSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 页面的ID
     */
    private Long moduleId;
    /**
     * 板块ID
     */
    private Long sectionDefId;
    /**
     * 短标题
     */
    private String shortTitle;
    /**
     * 长标题
     */
    private String longTitle;
    /**
     * 上下架
     */
    private Integer status;
    /**
     * 地址
     */
    private String url;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     *
     */
    private List<Long> sectionIds;
    /**
     * 定时
     */
    private Integer timing;
    private Date beginStartTime;
    private Date endStartTime;
    private Date beginEndTime;
    private Date endEndTime;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getSectionDefId() {
        return sectionDefId;
    }

    public void setSectionDefId(Long sectionDefId) {
        this.sectionDefId = sectionDefId;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public List<Long> getSectionIds() {
        return sectionIds;
    }

    public void setSectionIds(List<Long> sectionIds) {
        this.sectionIds = sectionIds;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Date getBeginStartTime() {
        return beginStartTime;
    }

    public void setBeginStartTime(Date beginStartTime) {
        this.beginStartTime = beginStartTime;
    }

    public Date getEndStartTime() {
        return endStartTime;
    }

    public void setEndStartTime(Date endStartTime) {
        this.endStartTime = endStartTime;
    }

    public Date getBeginEndTime() {
        return beginEndTime;
    }

    public void setBeginEndTime(Date beginEndTime) {
        this.beginEndTime = beginEndTime;
    }

    public Date getEndEndTime() {
        return endEndTime;
    }

    public void setEndEndTime(Date endEndTime) {
        this.endEndTime = endEndTime;
    }

}

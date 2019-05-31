package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class MessageDefSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 全站消息
     */
    private Integer global;
    /**
     * 是否超时
     */
    private Boolean overTime;
    /**
     * 开始创建时间
     */
    private Date startCreateDate;
    /**
     * 结束创建时间
     */
    private Date endCreateDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGlobal() {
        return global;
    }

    public void setGlobal(Integer global) {
        this.global = global;
    }

    public Boolean getOverTime() {
        return overTime;
    }

    public void setOverTime(Boolean overTime) {
        this.overTime = overTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

}

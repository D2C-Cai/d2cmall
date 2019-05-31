package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class ShareTaskDefSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private String orderByStr = "p.create_date DESC";
    /**
     * 状态
     */
    private Integer status;
    /**
     * 大于该状态
     */
    private Integer gtStatus;
    /**
     * 小于该状态
     */
    private Integer ltStatus;
    /**
     * 分享任务编号
     */
    private String sn;
    /**
     * 分享任务标题
     */
    private String title;
    /**
     * 开始时间大于该时间
     */
    private Date gtStartTime;
    /**
     * 开始时间小于该时间
     */
    private Date ltStartTime;

    public Date getGtStartTime() {
        return gtStartTime;
    }

    public void setGtStartTime(Date gtStartTime) {
        this.gtStartTime = gtStartTime;
    }

    public Date getLtStartTime() {
        return ltStartTime;
    }

    public void setLtStartTime(Date ltStartTime) {
        this.ltStartTime = ltStartTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGtStatus() {
        return gtStatus;
    }

    public void setGtStatus(Integer gtStatus) {
        this.gtStatus = gtStatus;
    }

    public Integer getLtStatus() {
        return ltStatus;
    }

    public void setLtStatus(Integer ltStatus) {
        this.ltStatus = ltStatus;
    }

    public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

}

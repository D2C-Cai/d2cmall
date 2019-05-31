package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 投诉处理单
 */
@Table(name = "o_complaint_track")
public class ComplaintTrack extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 投诉单Id
     */
    @AssertColumn("投诉单不能为空")
    private Long complaintId;
    /**
     * 处理时间
     */
    private Date dealDate;
    /**
     * 电话，短信，邮件，微信，qq
     */
    private String traceType;
    /**
     * 处理内容
     */
    private String dealContent;
    /**
     * 回访时间
     */
    private Date callBackDate;
    /**
     * 特殊说明
     */
    private String memo;
    /**
     * 处理状态(3、已完结， 2、已解决，1、未解决，-1 关闭)
     */
    private Integer trackStatus = 1;

    public Integer getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(Integer trackStatus) {
        this.trackStatus = trackStatus;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getTraceType() {
        return traceType;
    }

    public void setTraceType(String traceType) {
        this.traceType = traceType;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public Date getCallBackDate() {
        return callBackDate;
    }

    public void setCallBackDate(Date callBackDate) {
        this.callBackDate = callBackDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

}
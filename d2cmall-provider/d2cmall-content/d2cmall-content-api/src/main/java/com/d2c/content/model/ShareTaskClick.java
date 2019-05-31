package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -分享点击
 */
@Table(name = "v_share_task_click")
public class ShareTaskClick extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员任务ID
     */
    private Long taskId;
    /**
     * 任务定义ID
     */
    private Long taskDefId;
    /**
     * 标题
     */
    private String title;
    /**
     * IP
     */
    private String ip;
    /**
     * /** 0新建，1：统计
     */
    private Integer status = 0;
    /**
     * 终端
     */
    private String device = "MOBILE";
    /**
     * 页面来源
     */
    private String refer;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long shareTaskId) {
        this.taskId = shareTaskId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public Long getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(Long taskDefId) {
        this.taskDefId = taskDefId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

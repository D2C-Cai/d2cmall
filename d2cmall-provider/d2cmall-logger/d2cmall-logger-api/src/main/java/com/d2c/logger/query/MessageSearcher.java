package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MessageSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 0:未读 1：已读
     */
    private Integer status;
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 全站消息
     */
    private Integer global;
    /**
     * 是否超时
     */
    private Boolean overTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}

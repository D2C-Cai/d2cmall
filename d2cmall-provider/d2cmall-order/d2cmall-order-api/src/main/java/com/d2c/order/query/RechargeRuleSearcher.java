package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class RechargeRuleSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Date beginStartTime;
    /**
     * 开始时间
     */
    private Date endStartTime;
    /**
     * 状态
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

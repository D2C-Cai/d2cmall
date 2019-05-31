package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class AwardSessionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 开始查询时间
     */
    private Date beginStartDate;
    /**
     * 结束查询时间
     */
    private Date endStartDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginStartDate() {
        return beginStartDate;
    }

    public void setBeginStartDate(Date beginStartDate) {
        this.beginStartDate = beginStartDate;
    }

    public Date getEndStartDate() {
        return endStartDate;
    }

    public void setEndStartDate(Date endStartDate) {
        this.endStartDate = endStartDate;
    }

}

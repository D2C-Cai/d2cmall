package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class CollagePromotionSearcher extends BaseQuery {

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
     * 0 未开始，1进行中，-1已结束
     */
    private Integer tabIndex;
    /**
     * 开始时间开始
     */
    private Date beginDateStart;
    /**
     * 开始时间结束
     */
    private Date beginDateEnd;
    /**
     * 结束时间开始
     */
    private Date endDateStart;
    /**
     * 结束时间结束
     */
    private Date endDateEnd;

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

    public Integer getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(Integer tabIndex) {
        this.tabIndex = tabIndex;
    }

    public Date getBeginDateStart() {
        return beginDateStart;
    }

    public void setBeginDateStart(Date beginDateStart) {
        this.beginDateStart = beginDateStart;
    }

    public Date getBeginDateEnd() {
        return beginDateEnd;
    }

    public void setBeginDateEnd(Date beginDateEnd) {
        this.beginDateEnd = beginDateEnd;
    }

    public Date getEndDateStart() {
        return endDateStart;
    }

    public void setEndDateStart(Date endDateStart) {
        this.endDateStart = endDateStart;
    }

    public Date getEndDateEnd() {
        return endDateEnd;
    }

    public void setEndDateEnd(Date endDateEnd) {
        this.endDateEnd = endDateEnd;
    }

}

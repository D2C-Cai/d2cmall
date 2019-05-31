package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class SplashScreenSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private String name;
    private Date beginDate;
    private Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}

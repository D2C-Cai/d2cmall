package com.d2c.report.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.annotation.search.SearchIgnore;
import com.d2c.common.base.utils.DateUt;
import com.d2c.report.query.base.BasePartnerQuery;

public class PartnerSaleDayMgQuery extends BasePartnerQuery {

    private static final long serialVersionUID = -5017213275436783402L;
    /**
     * 日期 yyyy-MM-dd
     */
    @SearchField
    private String day;
    /**
     * {days}日之前
     */
    @SearchIgnore
    private Integer days;

    @Override
    public void init() {
        if (days != null) {
            this.day = DateUt.date2str(DateUt.dayBack(days));
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

}

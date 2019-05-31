package com.d2c.report.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.annotation.search.SearchIgnore;
import com.d2c.common.base.utils.DateUt;
import com.d2c.report.query.base.BasePartnerQuery;

public class PartnerSaleMonthMgQuery extends BasePartnerQuery {

    private static final long serialVersionUID = -5017213275436783402L;
    /**
     * 日期 yyyy-MM
     */
    @SearchField
    private String month;
    /**
     * {months}月之前
     */
    @SearchIgnore
    private Integer months;

    @Override
    public void init() {
        if (months != null) {
            this.month = DateUt.month2str(DateUt.monthBack(months));
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

}

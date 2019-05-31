package com.d2c.report.query;

import com.d2c.common.api.query.model.BaseQuery;

public class WalletSummarySearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 查询年
     */
    private Integer year;
    /**
     * 查询月
     */
    private Integer month;
    /**
     * 1收入；-1支出；
     */
    private Integer direction;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

}

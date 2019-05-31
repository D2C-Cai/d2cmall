package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class MemberSearchInfoSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 是否汇总
     */
    private Integer statistic;
    /**
     * 创建时间开始
     */
    private Date beginCreateDate;
    /**
     * 创建时间结束
     */
    private Date endCreateDate;

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatistic() {
        return statistic;
    }

    public void setStatistic(Integer statistic) {
        this.statistic = statistic;
    }

}

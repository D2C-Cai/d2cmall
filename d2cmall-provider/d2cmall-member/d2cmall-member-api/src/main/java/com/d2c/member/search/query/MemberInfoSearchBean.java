package com.d2c.member.search.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class MemberInfoSearchBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 查询数量 ，默认前100
     */
    private Integer top = 100;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

}

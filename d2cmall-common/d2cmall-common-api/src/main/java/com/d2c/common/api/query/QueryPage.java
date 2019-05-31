package com.d2c.common.api.query;

import com.d2c.common.base.utils.JsonUt;

import java.util.List;

/**
 * 查询参数封装
 *
 * @author wull
 */
public class QueryPage {

    private Integer page;
    private Integer limit;
    private List<QueryItem> querys;

    public QueryPage() {
    }

    public QueryPage(List<QueryItem> querys, Integer page, Integer limit) {
        this.querys = querys;
        this.page = page;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "查询参数: " + JsonUt.serialize(querys) + " page:" + page + " limit:" + limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<QueryItem> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QueryItem> querys) {
        this.querys = querys;
    }

}

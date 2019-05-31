package com.d2c.common.api.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.model.BaseQuery;

import java.util.List;

public interface ListService<T> extends BaseService<T> {

    public PageResult<T> findPageResult(BaseQuery query, PageModel pager);

    public int count(BaseQuery query);

    public int count(List<QueryItem> queryList);

    public List<T> findQuery(Pager pager);

    public List<T> findQuery(BaseQuery query, Pager pager);

    public List<T> findQuery(List<QueryItem> queryList, Integer page, Integer limit);

}

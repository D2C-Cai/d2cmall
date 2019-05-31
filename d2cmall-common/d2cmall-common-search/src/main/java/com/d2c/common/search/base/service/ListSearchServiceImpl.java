package com.d2c.common.search.base.service;

import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.query.model.EsQuery;
import com.d2c.common.search.base.ListSearchDao;
import com.d2c.common.search.model.ParentSearchDO;
import com.d2c.common.search.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class ListSearchServiceImpl<T extends ParentSearchDO<ID>, ID extends Serializable, R extends BaseRepository<T, ID>>
        extends BaseSearchServiceImpl<T, ID, R> implements ListSearchService<T, ID> {

    @Autowired
    protected ListSearchDao<T, ID, R> searchDao;

    public PageResult<T> findByPage(EsQuery query) {
        return searchDao.findByPage(query);
    }

    public PageResult<T> findByPage(EsQuery query, Pager page) {
        return searchDao.findByPage(query, page);
    }

}

package com.d2c.common.search.base;

import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.page.convert.PageConvert;
import com.d2c.common.api.query.model.EsQuery;
import com.d2c.common.search.model.ParentSearchDO;
import com.d2c.common.search.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class ListSearchDao<T extends ParentSearchDO<ID>, ID extends Serializable, R extends BaseRepository<T, ID>> extends BaseSearchDao<T, ID, R> {

    @Autowired
    protected SearchTemplate searchTemplate;

    public PageResult<T> findByPage(EsQuery query) {
        return findByPage(query, null);
    }

    public PageResult<T> findByPage(EsQuery query, Pager page) {
        return resultPage(searchTemplate.findByPage(query, PageConvert.convert(page), getTypeClazz()));
    }

}

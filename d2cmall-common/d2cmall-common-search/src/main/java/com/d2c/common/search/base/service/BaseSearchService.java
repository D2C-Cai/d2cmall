package com.d2c.common.search.base.service;

import com.d2c.common.api.page.PageResult;
import com.d2c.common.search.model.ParentSearchDO;

import java.io.Serializable;
import java.util.List;

public interface BaseSearchService<T extends ParentSearchDO<ID>, ID extends Serializable> {

    public T getById(ID id);

    public PageResult<T> findByPage(int page, int limit);

    public T save(T bean);

    public List<T> save(List<T> list);

    public boolean update(ID id, Object... source);

    public void deleteById(ID id);

    public T getByField(String fieldName, Object value);

}

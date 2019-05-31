package com.d2c.common.search.base.service;

import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.search.base.BaseSearchDao;
import com.d2c.common.search.model.ParentSearchDO;
import com.d2c.common.search.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class BaseSearchServiceImpl<T extends ParentSearchDO<ID>, ID extends Serializable, R extends BaseRepository<T, ID>>
        implements BaseSearchService<T, ID> {

    @Autowired
    protected BaseSearchDao<T, ID, R> searchDao;

    public T getById(ID id) {
        return searchDao.getById(id);
    }

    public PageResult<T> findByPage(Pager page) {
        return searchDao.findByPage(page);
    }

    public PageResult<T> findByPage(int page, int limit) {
        return searchDao.findByPage(page, limit);
    }

    public T save(T bean) {
        return searchDao.save(bean);
    }

    public List<T> save(List<T> list) {
        return (List<T>) searchDao.saveAll(list);
    }

    public boolean update(ID id, Object... source) {
        return searchDao.update(id, source);
    }

    public void deleteById(ID id) {
        searchDao.deleteById(id);
    }

    public T getByField(String fieldName, Object value) {
        return searchDao.getByField(fieldName, value);
    }

}

package com.d2c.common.mongodb.base;

import com.d2c.common.core.base.GenericAble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BaseMongoService<T> extends GenericAble<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected BaseMongoDao<T> baseMongoDao;

    public T insert(T entity) {
        return baseMongoDao.insert(entity);
    }

    public T save(T entity) {
        return baseMongoDao.save(entity);
    }

    public void saveAll(List<T> list) {
        baseMongoDao.saveAll(list);
    }

    public T findById(Object id) {
        return baseMongoDao.findById(id);
    }

    public List<T> findLimit(Query query, Integer limit) {
        return baseMongoDao.findLimit(query, limit);
    }

    public List<T> findPage(Query query, Integer page, Integer limit) {
        return baseMongoDao.findPage(query, page, limit);
    }

    public List<T> findPage(Query query, Integer page, Integer limit, String orderField, boolean isAsc) {
        return baseMongoDao.findPage(query, page, limit, orderField, isAsc);
    }

    public List<T> findAll() {
        return baseMongoDao.findAll();
    }

    public void cleanAll() {
        baseMongoDao.cleanAll();
    }

}

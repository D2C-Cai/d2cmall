package com.d2c.common.api.service;

import java.util.List;

public interface BaseService<T> {

    public T save(T bean);

    public List<T> saveAll(List<T> list);

    public int deleteById(Object id);

    public int deleteAll();

    public int updateNotNull(T bean);

    public int updateFieldById(Integer id, String key, Object value);

    public List<T> updateAll(List<T> list);

    public T findOneById(Object id);

    public int countAll();

    public boolean isEmpty();

    public List<T> findPage(Integer page, Integer limit);

    public List<T> findByFieldName(String fieldName, Object fieldValue);

    public T findOneByFieldName(String fieldName, Object fieldValue);

    public <E> List<E> findByFieldName(String key, Object value, String result, Class<E> resultClz);

    public <E> E findOneByFieldName(String key, Object value, String result, Class<E> resultClz);

}

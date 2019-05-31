package com.d2c.common.search.base;

import com.d2c.common.api.model.BaseParentDO;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.page.convert.PageConvert;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.core.base.GenericAble;
import com.d2c.common.search.convert.BaseSearchConvert;
import com.d2c.common.search.model.ParentSearchDO;
import com.d2c.common.search.repository.BaseRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseSearchDao<T extends ParentSearchDO<ID>, ID extends Serializable, R extends BaseRepository<T, ID>> extends GenericAble<T> {

    @Autowired
    protected R repository;
    @Autowired
    protected SearchTemplate searchTemplate;
    //************************** @Override *************************

    /**
     * 注入转换器 (重写方法), convert 和 convertAll方法调用
     */
    public <E extends BaseParentDO<?>> BaseSearchConvert<T, E> getConvert() {
        return null;
    }
    //************************** public ****************************

    public T getById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public List<T> findAllList() {
        return findAll().getList();
    }

    public PageResult<T> findAll() {
        return resultPage(repository.findAll());
    }

    public PageResult<T> findByPage(Pager pager) {
        return resultPage(repository.findAll(PageConvert.convert(pager)));
    }

    public PageResult<T> findByPage(int page, int limit) {
        return resultPage(repository.findAll(PageConvert.convert(page, limit)));
    }

    public T save(T bean) {
        return repository.save(bean);
    }

    public <E extends BaseParentDO<?>> T save(E entity) {
        return save(convert(entity));
    }

    public <S extends T> Iterable<S> saveAll(Iterable<S> list) {
        return repository.saveAll(list);
    }

    public boolean update(ID id, Object... source) {
        return searchTemplate.update(id, getTypeClazz(), source);
    }

    public void delete(T data) {
        repository.delete(data);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public <E extends BaseParentDO<?>> T convert(E entity) {
        if (getConvert() != null) {
            return getConvert().convertTo(entity);
        }
        return ConvertUt.convertBean(entity, getTypeClazz());
    }

    public <E extends BaseParentDO<?>> List<T> convertAll(List<E> entitys) {
        List<T> list = new ArrayList<>();
        entitys.forEach(entity -> {
            list.add(convert(entity));
        });
        return list;
    }

    public T getByField(String fieldName, Object value) {
        try {
            return repository.search(QueryBuilders.matchQuery(fieldName, value)).iterator().next();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    //*******************************************

    protected PageResult<T> resultPage(Page<T> page) {
        return new PageResult<T>(page);
    }

    protected PageResult<T> resultPage(Iterable<T> page) {
        return new PageResult<T>((Page<T>) page);
    }

}

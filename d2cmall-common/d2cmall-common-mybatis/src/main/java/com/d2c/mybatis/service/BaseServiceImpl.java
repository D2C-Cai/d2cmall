/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.d2c.mybatis.service;

import com.d2c.common.api.model.BaseDO;
import com.d2c.common.api.model.BaseParentDO;
import com.d2c.common.api.model.DeleteAble;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.api.service.BaseService;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.core.base.GenericAble;
import com.d2c.mybatis.handler.ValidateHandler;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.mybatis.page.PageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Mybatis服务层基础类
 */
public abstract class BaseServiceImpl<T extends BaseParentDO<?>> extends GenericAble<T> implements BaseService<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected SuperMapper<T> mapper;
    @Autowired
    protected ValidateHandler validateHandler;

    /**
     * 保存实体
     */
    @Override
    public T save(T bean) {
        AssertUt.notNull(bean);
        validateHandler.check(bean);
        if (bean instanceof BaseDO) {
            BaseDO e = (BaseDO) bean;
            Date now = new Date();
            e.setGmtCreate(now);
            e.setGmtModified(now);
        } else if (bean instanceof PreUserDO) {
            PreUserDO e = (PreUserDO) bean;
            Date now = new Date();
            e.setCreateDate(now);
            e.setModifyDate(now);
        }
        // if (entity instanceof BaseUserDO) {
        // BaseUserDO e = (BaseUserDO) entity;
        // String userName = UserHolder.getUserName();
        // e.setCreator(userName);
        // e.setModifier(userName);
        // }
        mapper.insert(bean);
        return bean;
    }

    @Override
    public List<T> saveAll(List<T> list) {
        for (T bean : list) {
            save(bean);
        }
        return list;
    }

    /**
     * 删除实体, DeleteAble接口实体进行假删
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public int deleteById(Object id) {
        AssertUt.notNull(id);
        if (getTypeClazz().isInstance(DeleteAble.class)) {
            T bean = BeanUt.newInstance(getTypeClazz());
            ((BaseParentDO) bean).setId(id);
            ((DeleteAble) bean).setIsDeleted(true);
            return updateNotNull(bean);
        } else {
            return mapper.deleteByPrimaryKey(id);
        }
    }

    public int delete(T bean) {
        return deleteById(bean.getId());
    }

    public int deleteAll() {
        return mapper.deleteByCondition(null);
    }

    /**
     * 修改实体，更新属性不为null的值
     */
    @Override
    public int updateNotNull(T bean) {
        AssertUt.notNull(bean);
        validateHandler.check(bean);
        if (bean instanceof BaseDO) {
            BaseDO e = (BaseDO) bean;
            e.setGmtModified(new Date());
        } else if (bean instanceof PreUserDO) {
            PreUserDO e = (PreUserDO) bean;
            e.setModifyDate(new Date());
        }
        // if (entity instanceof BaseUserDO) {
        // BaseUserDO e = (BaseUserDO) entity;
        // e.setModifier(UserHolder.getUserName());
        // }
        return mapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public int updateFieldById(Integer id, String key, Object value) {
        return mapper.updateFieldById(id, StringUt.humpToLine(key), value);
    }

    @Override
    public List<T> updateAll(List<T> list) {
        for (T bean : list) {
            updateNotNull(bean);
        }
        return list;
    }

    @Override
    public int countAll() {
        return mapper.selectCountByCondition(null);
    }

    @Override
    public boolean isEmpty() {
        return countAll() <= 0;
    }

    @Override
    public T findOneById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findPage(Integer page, Integer limit) {
        return mapper.selectByRowBounds(null, PageHandler.init(page, limit));
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 根据单一字段查询
     */
    public List<T> findByFieldName(String fieldName, Object fieldValue) {
        return mapper.selectByFieldName(StringUt.humpToLine(fieldName), fieldValue);
    }

    public T findOneByFieldName(String fieldName, Object fieldValue) {
        return mapper.selectOneByFieldName(StringUt.humpToLine(fieldName), fieldValue);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByFieldName(String key, Object value, String result, Class<E> resultClz) {
        Object list = mapper.selectResultByFieldName(StringUt.humpToLine(key), value, StringUt.humpToLine(result));
        return (List<E>) list;
    }

    @SuppressWarnings("unchecked")
    public <E> E findOneByFieldName(String key, Object value, String result, Class<E> resultClz) {
        return (E) mapper.selectOneResultByFieldName(StringUt.humpToLine(key), value, StringUt.humpToLine(result));
    }

}

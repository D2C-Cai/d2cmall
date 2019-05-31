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

import com.d2c.common.api.model.BaseParentDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.page.base.PageSort;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.convert.QueryConvert;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.service.ListService;
import com.d2c.mybatis.page.PageHandler;
import com.d2c.mybatis.query.QueryResolver;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Mybatis服务层基础类
 */
@Service
public abstract class ListServiceImpl<T extends BaseParentDO<?>> extends BaseServiceImpl<T> implements ListService<T> {

    @Autowired
    private QueryResolver queryResolver;

    @Override
    public PageResult<T> findPageResult(BaseQuery query, PageModel pager) {
        return new PageResult<>(pager, findQuery(query, pager), count(query));
    }

    @Override
    public int count(BaseQuery query) {
        return count(QueryConvert.convert(query));
    }

    @Override
    public int count(List<QueryItem> queryItems) {
        return mapper.selectCountByCondition(getCondition(queryItems));
    }

    @Override
    public List<T> findQuery(BaseQuery query, Pager pager) {
        return findQueryImpl(query, pager);
    }

    @Override
    public List<T> findQuery(Pager pager) {
        return findQueryImpl(null, pager);
    }

    @Override
    public List<T> findQuery(List<QueryItem> queryItems, Integer page, Integer limit) {
        return findQueryImpl(getCondition(queryItems), PageHandler.init(page, limit));
    }

    private List<T> findQueryImpl(BaseQuery query, Pager pager) {
        Condition condition = getCondition(query);
        PageSort pageSort = pager.getPageSort();
        if (pageSort != null) {
            if (condition == null) {
                condition = new Condition(getTypeClazz());
            }
            condition.setOrderByClause(pageSort.toSql());
        }
        return findQueryImpl(condition, PageHandler.init(pager));
    }

    private List<T> findQueryImpl(Condition condition, RowBounds pageBounds) {
        return mapper.selectByConditionAndRowBounds(condition, pageBounds);
    }
    // *************************** private ************************************

    private Condition getCondition(BaseQuery query) {
        return getCondition(QueryConvert.convert(query));
    }

    private Condition getCondition(List<QueryItem> queryItems) {
        if (queryItems == null || queryItems.isEmpty()) {
            return null;
        }
        return queryResolver.getCondition(this.getTypeClazz(), queryItems);
    }

}

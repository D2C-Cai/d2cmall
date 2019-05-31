package com.d2c.common.mongodb.base;

import com.d2c.common.api.page.Pager;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.convert.QueryConvert;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.mongodb.query.QueryMongoResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ListMongoDao<T> extends BaseMongoDao<T> {

    @Autowired
    protected QueryMongoResolver queryMongoResolver;

    public long count(Query query, MongoQuery queryBean) {
        return count(makeQuery(query, queryBean));
    }

    public long count(MongoQuery queryBean) {
        return count(makeQuery(null, queryBean));
    }

    public long count(List<QueryItem> queryItems) {
        return count(makeQuery(queryItems));
    }

    public List<T> findQueryPage(MongoQuery queryBean, Pager pager) {
        return findPage(makeQuery(null, queryBean), pager);
    }

    public List<T> findQueryPage(Query query, MongoQuery queryBean, Pager pager) {
        return findPage(makeQuery(query, queryBean), pager);
    }

    public List<T> findQueryPage(List<QueryItem> queryItems, Integer page, Integer limit) {
        return findPage(makeQuery(queryItems), page, limit);
    }

    public List<T> findQueryPage(Query query, MongoQuery queryBean, Integer page, Integer limit) {
        return findPage(makeQuery(query, queryBean), page, limit);
    }

    public List<T> findQueryPage(Query query, MongoQuery queryBean, Integer page, Integer limit, String orderField, boolean isAsc) {
        return findPage(makeQuery(query, queryBean), page, limit, orderField, isAsc);
    }
    // *************************** private ************************************

    protected Query makeQuery(List<QueryItem> queryItems) {
        return queryMongoResolver.makeQuery(queryItems);
    }

    protected Query makeQuery(Query query, MongoQuery queryBean) {
        return queryMongoResolver.makeQuery(query, QueryConvert.convert(queryBean));
    }

}

package com.d2c.common.search.base;

import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.convert.QueryConvert;
import com.d2c.common.api.query.model.EsQuery;
import com.d2c.common.search.query.QueryBuilderResolver;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class SearchTemplate extends BaseSearchTemplate {

    @Autowired
    private QueryBuilderResolver qbResolver;

    public SearchTemplate(Client client) {
        super(client);
    }

    public <T> Page<T> findByPage(EsQuery query, Class<T> clazz) {
        return findByPage(query, null, clazz);
    }

    public <T> Page<T> findByPage(EsQuery query, Pageable page, Class<T> clazz) {
        return findByPage(QueryConvert.convert(query), page, clazz);
    }

    private <T> Page<T> findByPage(List<QueryItem> queryItems, Pageable page, Class<T> clazz) {
        SearchQuery query = new NativeSearchQueryBuilder().withQuery(qbResolver.makeQuery(queryItems))
                .withPageable(page)
                .build();
        return queryForPage(query, clazz);
    }

    public boolean update(Object id, Class<?> clazz, Object... source) {
        if (id == null) {
            return false;
        }
        UpdateQuery query = new UpdateQuery();
        query.setClazz(clazz);
        query.setId(id.toString());
        UpdateRequest up = new UpdateRequest();
        IndexRequest req = new IndexRequest();
        req.source(source);
        up.doc(req);
        query.setUpdateRequest(up);
        this.update(query);
        return true;
    }
    //***************** Compatible **********************

    public void delete(String index, String type, QueryBuilder query) {
        DeleteQuery item = new DeleteQuery();
        item.setIndex(index);
        item.setType(type);
        item.setQuery(query);
        delete(item);
        refresh(index);
    }

    public void deleteAll(String index, String type) {
        delete(index, type, matchAllQuery());
    }

}

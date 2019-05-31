package com.d2c.common.search.query;

import com.d2c.common.api.annotation.constant.SearchParamConst;
import com.d2c.common.api.query.BaseQueryResolver;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.QueryParam;
import com.d2c.common.api.query.enums.OperType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Elasticsearch 搜索 Query 解析器
 *
 * @author wull
 */
@Component
public class QueryBuilderResolver extends BaseQueryResolver {

    public QueryBuilder makeQuery(List<QueryItem> queryItems) {
        return makeQueryImpl(queryItems, boolQuery());
    }

    private BoolQueryBuilder makeQueryImpl(List<QueryItem> queryItems, BoolQueryBuilder query) {
        QueryBuilder qb = null;
        for (QueryItem item : queryItems) {
            switch (item.getOperator()) {
                case DISTINCT:
                case ORDER_BY:
                    break;
                case OR:
                    query.should(makeQueryImpl(getChildrens(item), boolQuery()));
                    break;
                case NOT_EQUAL:
                case NOT_NULL:
                    qb = makeQueryBuilder(item);
                    if (qb != null) {
                        query.mustNot(qb);
                    }
                    break;
                default:
                    qb = makeQueryBuilder(item);
                    if (qb != null) {
                        query.must(qb);
                    }
                    break;
            }
        }
        return query;
    }

    private QueryBuilder makeQueryBuilder(QueryItem item) {
        String name = item.getName();
        Object value = getValue(item.getValue());
        if (value == null) return null;
        Iterable<?> values = null;
        if (value instanceof Iterable) {
            values = (Iterable<?>) value;
            if (OperType.EQUAL.equals(item.getOperator())) {
                item.setOperator(OperType.IN);
            }
        }
        switch (item.getOperator()) {
            case IS_NULL:
            case EQUAL:
            case NOT_EQUAL:
            case NOT_NULL:
                return queryStringQuery(value.toString()).field(name).defaultOperator(QueryStringQueryBuilder.Operator.AND);
            case LIKE:
                return queryStringQuery("*" + value.toString() + "*").field(name).analyzeWildcard(true);
            case GT:
                return rangeQuery(name).gt(value);
            case LT:
                return rangeQuery(name).lt(value);
            case GTE:
                return rangeQuery(name).gte(value);
            case LTE:
                return rangeQuery(name).lte(value);
            case BETWEEN:
                if (values != null) {
                    Iterator<?> its = values.iterator();
                    Object frist = its.next();
                    Object second = its.next();
                    if (frist != null && second != null) {
                        return rangeQuery(name).lte(frist).gte(second);
                    }
                    if (frist != null) {
                        return rangeQuery(name).gte(value);
                    }
                    if (second != null) {
                        return rangeQuery(name).lte(value);
                    }
                } else {
                    return rangeQuery(name).gte(value);
                }
            case IN:
                if (values != null && !isEmpty(values)) {
                    BoolQueryBuilder bq = boolQuery();
                    values.forEach(bean -> {
                        bq.should(queryStringQuery(bean.toString()).field(name));
                    });
                    return bq;
                }
                break;
            case NOT_IN:
                if (values != null && !isEmpty(values)) {
                    BoolQueryBuilder bq = boolQuery();
                    values.forEach(bean -> {
                        bq.mustNot(queryStringQuery(bean.toString()).field(name));
                    });
                    return bq;
                }
                break;
            case FUZZY:
                return fuzzyQuery(name, value);
            case MULTI_MATCH:
                QueryParam it = item.getParam(SearchParamConst.MULTI_MATCH_FIELDS);
                if (it == null) return null;
                MultiMatchQueryBuilder mqb = new MultiMatchQueryBuilder(value, it.getValues());
                it = item.getParam(SearchParamConst.ANALYZER);
                if (it != null) {
                    mqb.analyzer(it.getValue());
                }
                it = item.getParam(SearchParamConst.MIN_SHOULD_MATCH);
                if (it != null) {
                    mqb.minimumShouldMatch(it.getValue());
                }
                return mqb;
            default:
                break;
        }
        return null;
    }
    //************************************************************************

    private Object getValue(Object value) {
        if (value == null) return null;
        if (value instanceof Date) {
            return ((Date) value).getTime();
        }
        return value;
    }

    private boolean isEmpty(Iterable<?> values) {
        return !values.iterator().hasNext();
    }

    private List<QueryItem> getChildrens(QueryItem query) {
        List<QueryItem> list = new ArrayList<QueryItem>();
        if (query.getChildren() != null) {
            for (QueryItem bean : query.getChildren()) {
                list.add(bean);
            }
        }
        return list;
    }

}

package com.d2c.common.search.query;

import com.d2c.common.api.query.BaseQueryResolver;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.enums.SortType;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Elasticsearch 搜索 Query 解析器
 *
 * @author wull
 */
@Component
public class CriteriaQueryResolver extends BaseQueryResolver {

    public CriteriaQuery makeQuery(List<QueryItem> queryItems) {
        return makeQueryImpl(queryItems, new CriteriaQuery(new Criteria()));
    }

    private CriteriaQuery makeQueryImpl(List<QueryItem> queryItems, CriteriaQuery query) {
        Criteria cri = query.getCriteria();
        for (QueryItem item : queryItems) {
            switch (item.getOperator()) {
                case DISTINCT:
                    break;
                case OR:
                    Criteria c = new Criteria();
                    for (QueryItem s : getChildrens(item)) {
                        c = makeCriteria(s, c);
                    }
                    cri.or(c);
                    break;
                case ORDER_BY:
                    if (item.getValue() != null) {
                        Direction sort = Direction.DESC;
                        if (item.getSortType().equals(SortType.ASC)) {
                            sort = Direction.ASC;
                        }
                        query.addSort(new Sort(sort, item.getValue().toString()));
                    }
                    break;
                default:
                    query.addCriteria(makeCriteria(item, cri));
                    break;
            }
        }
        return query;
    }

    private Criteria makeCriteria(QueryItem query, Criteria cri) {
        String name = query.getName();
        Object value = query.getValue();
        if (value == null) return cri;
        Iterable<?> values = null;
        if (value instanceof Iterable) {
            values = (Iterable<?>) value;
            if (OperType.EQUAL.equals(query.getOperator())) {
                query.setOperator(OperType.IN);
            }
        }
        switch (query.getOperator()) {
            case IS_NULL:
            case EQUAL:
                return cri.and(name).is(value);
            case NOT_EQUAL:
            case NOT_NULL:
                return cri.and(name).notIn(value);
            case LIKE:
                return cri.and(name).contains(value.toString());
            case GT:
                return cri.and(name).greaterThan(value);
            case LT:
                return cri.and(name).lessThan(value);
            case GTE:
                return cri.and(name).greaterThanEqual(value);
            case LTE:
                return cri.and(name).lessThanEqual(value);
            case BETWEEN:
                if (values != null) {
                    Iterator<?> its = values.iterator();
                    Object frist = its.next();
                    Object second = its.next();
                    if (frist != null && second != null) {
                        return cri.and(name).between(frist, second);
                    }
                    if (frist != null) {
                        return cri.and(name).greaterThanEqual(value);
                    }
                    if (second != null) {
                        return cri.and(name).lessThanEqual(value);
                    }
                } else {
                    return cri.and(name).greaterThanEqual(value);
                }
            case IN:
                if (values != null && !isEmpty(values)) {
                    return cri.and(name).in(values);
                }
                break;
            case NOT_IN:
                if (values != null && !isEmpty(values)) {
                    return cri.and(name).notIn(values);
                }
                break;
            case FUZZY:
                return cri.and(name).fuzzy(value.toString());
            default:
                break;
        }
        return cri;
    }
    //************************************************************************

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

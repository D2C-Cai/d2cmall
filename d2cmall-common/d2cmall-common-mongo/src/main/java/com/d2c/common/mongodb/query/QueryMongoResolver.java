package com.d2c.common.mongodb.query;

import com.d2c.common.api.query.BaseQueryResolver;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.enums.SortType;
import com.d2c.common.base.utils.ConvertUt;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索DAO支撑
 *
 * @author wull
 */
@Component
public class QueryMongoResolver extends BaseQueryResolver {

    public Query makeQuery(List<QueryItem> queryItems) {
        return makeQuery(new Query(), queryItems);
    }

    public Query makeQuery(Query query, List<QueryItem> queryItems) {
        return makeQueryImpl(query, queryItems);
    }
    //***************************************************

    private Query makeQueryImpl(Query query, List<QueryItem> queryItems) {
        if (queryItems == null) return query;
        if (query == null) {
            query = new Query();
        }
        Map<String, Criteria> map = new LinkedHashMap<>();
        Criteria cri = new Criteria();
        for (QueryItem item : queryItems) {
            switch (item.getOperator()) {
                case DISTINCT:
                    break;
                case OR:
                    List<Criteria> clist = new ArrayList<>();
                    Map<String, Criteria> cmap = new LinkedHashMap<>();
                    for (QueryItem s : getChildrens(item)) {
                        clist.add(makeCriteria(cmap, s, new Criteria()));
                    }
                    cri.orOperator(clist.toArray(new Criteria[]{}));
                    break;
                case ORDER_BY:
                    if (item.getValue() != null) {
                        Direction sort = Direction.DESC;
                        if (item.getSortType().equals(SortType.ASC)) {
                            sort = Direction.ASC;
                        }
                        query.with(new Sort(sort, item.getValue().toString()));
                    }
                    break;
                default:
                    cri = makeCriteria(map, item, cri);
                    break;
            }
        }
        for (Criteria c : map.values()) {
            query.addCriteria(c);
        }
        return query;
    }

    private Criteria makeCriteria(Map<String, Criteria> map, QueryItem query, Criteria cri) {
        String name = query.getName();
        Object value = query.getValue();
        if (value == null) return cri;
        List<?> values = null;
        if (value instanceof List) {
            values = (List<?>) value;
        }
        switch (query.getOperator()) {
            case IS_NULL:
                if (ConvertUt.toBoolean(value)) {
                    return and(map, cri, name).is(null);
                } else {
                    return and(map, cri, name).ne(null);
                }
            case NOT_NULL:
                if (ConvertUt.toBoolean(value)) {
                    return and(map, cri, name).ne(null);
                } else {
                    return and(map, cri, name).is(null);
                }
            case EQUAL:
                return and(map, cri, name).is(value);
            case NOT_EQUAL:
                return and(map, cri, name).ne(value);
            case LIKE:
                return and(map, cri, name).regex(value.toString());
            case GT:
                return and(map, cri, name).gt(value);
            case LT:
                return and(map, cri, name).lt(value);
            case GTE:
                return and(map, cri, name).gte(value);
            case LTE:
                return and(map, cri, name).lte(value);
            case BETWEEN:
                if (values != null) {
                    if (values.size() == 1) {
                        return and(map, cri, name).gte(value);
                    } else if (values.size() >= 2) {
                        if (values.get(0) != null && !"".equals(values.get(0))) {
                            return and(map, cri, name).gte(values.get(0)).lte(values.get(1));
                        } else {
                            return and(map, cri, name).lte(value);
                        }
                    } else {
                        return cri;
                    }
                } else {
                    return and(map, cri, name).gte(value);
                }
            case IN:
                if (values != null && !values.isEmpty()) {
                    return and(map, cri, name).in(values);
                }
                break;
            case NOT_IN:
                if (values != null && !values.isEmpty()) {
                    return and(map, cri, name).nin(values);
                }
                break;
            default:
                break;
        }
        return cri;
    }
    //************************************************************************

    private Criteria and(Map<String, Criteria> map, Criteria cri, String name) {
        Criteria c = map.get(name);
        if (c == null) {
            c = cri.and(name);
            map.put(name, c);
        }
        return c;
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

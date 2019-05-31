package com.d2c.mybatis.query;

import com.d2c.common.api.query.BaseQueryResolver;
import com.d2c.common.api.query.QueryItem;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索DAO支撑
 *
 * @author wull
 */
@Component
public class QueryResolver extends BaseQueryResolver {

    public Condition getCondition(Class<?> clz, List<QueryItem> queryItems) {
        Condition condition = new Condition(clz);
        Criteria criteria = condition.createCriteria();
        return getCondition(queryItems, condition, criteria);
    }

    private Condition getCondition(List<QueryItem> queryItems, Condition condition, Criteria criteria) {
        for (QueryItem item : queryItems) {
            switch (item.getOperator()) {
                case DISTINCT:
                    condition.setDistinct(true);
                    break;
                case ORDER_BY:
                    condition.setOrderByClause(item.getValue() + " " + item.getSortType().name());
                    break;
                case OR:
                    getCondition(getChildrens(item), condition, condition.or());
                    break;
                default:
                    buildCriteria(item, criteria);
                    break;
            }
        }
        return condition;
    }

    private Criteria buildCriteria(QueryItem query, Criteria criteria) {
        String name = query.getName();
        Object value = query.getValue();
        if (value == null) return criteria;
        List<?> values = null;
        if (value instanceof List) {
            values = (List<?>) value;
        }
        switch (query.getOperator()) {
            case IS_NULL:
                return criteria.andIsNull(name);
            case NOT_NULL:
                return criteria.andIsNotNull(name);
            case EQUAL:
                return criteria.andEqualTo(name, value);
            case NOT_EQUAL:
                return criteria.andNotEqualTo(name, value);
            case LIKE:
                return criteria.andLike(name, getLikeStr(value));
            case NOT_LIKE:
                return criteria.andNotLike(name, value.toString());
            case GT:
                return criteria.andGreaterThan(name, value);
            case LT:
                return criteria.andLessThan(name, value);
            case GTE:
                return criteria.andGreaterThanOrEqualTo(name, value);
            case LTE:
                return criteria.andLessThanOrEqualTo(name, value);
            case BETWEEN:
                if (values != null) {
                    if (values.size() == 1) {
                        return criteria.andGreaterThan(name, values.get(0));
                    } else if (values.size() >= 2) {
                        if (values.get(0) != null && !"".equals(values.get(0))) {
                            return criteria.andBetween(name, values.get(0), values.get(1));
                        } else {
                            return criteria.andLessThan(name, values.get(1));
                        }
                    } else {
                        return criteria;
                    }
                } else {
                    return criteria.andGreaterThan(name, value);
                }
            case IN:
                if (values != null && !values.isEmpty()) {
                    return criteria.andIn(name, values);
                }
            case NOT_IN:
                if (values != null && !values.isEmpty()) {
                    return criteria.andNotIn(name, values);
                }
            default:
                break;
        }
        return criteria;
    }

    private String getLikeStr(Object value) {
        return "%" + value.toString() + "%";
    }
    //************************************************************************

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

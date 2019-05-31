package com.d2c.common.core.query;

import com.d2c.common.api.json.Json;
import com.d2c.common.api.json.JsonBean;
import com.d2c.common.api.json.JsonList;
import com.d2c.common.base.exception.QueryException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.ConvertUt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map.Entry;

/**
 * 查询条件解析器
 *
 * @see MyCriteria
 */
public class MyCriteriaResolver {

    protected static final Logger logger = LoggerFactory.getLogger(MyCriteriaResolver.class);

    /**
     * 校验查询
     *
     * @param query
     * @param obj
     */
    public static boolean check(Json query, Object obj) {
        try {
            checkImpl(query, obj);
            return true;
        } catch (QueryException e) {
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 校验对象obj是否符合query查询标准
     * <p>校验未通过抛出 {@link QueryException}
     *
     * @param query
     * @param obj
     * @return value obj对象按query.key解析值
     */
    private static void checkImpl(Json query, Object obj) {
        if (query instanceof JsonList) {
            JsonList list = (JsonList) query;
            for (Object v : list) {
                if (v instanceof JsonBean) {
                    checkImpl(query, obj);
                }
            }
        } else {
            JsonBean bean = (JsonBean) query;
            for (Entry<String, Object> en : bean.entrySet()) {
                if (en.getValue() instanceof JsonBean) {
                    Object o = BeanUt.getValueExpr(obj, en.getKey());
                    logger.debug("解析扩展... {'" + en.getKey() + "':" + en.getValue() + "} -> " + o);
                    checkImpl((JsonBean) en.getValue(), o);
                } else {
                    Object o = null;
                    if (en.getKey().startsWith("$")) {
                        o = obj;
                    } else {
                        o = BeanUt.getValueExpr(obj, en.getKey());
                    }
                    if (!operCheck(en.getKey(), o, en.getValue())) {
                        logger.debug("解析未通过... " + query + " -> " + en.getKey() + " " + en.getValue() + " " + o);
                        throw new QueryException();
                    }
                    logger.debug("解析通过... " + query + " -> " + en.getKey() + " " + en.getValue() + " " + o);
                }
            }
        }
    }

    /**
     * 判断是否通过查询操作
     *
     * @param key   操作符
     * @param obj   解析对象
     * @param value map.value比对值
     * @return boolean
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static boolean operCheck(String key, Object obj, Object value) {
        if (!key.startsWith("$") || key.equals("$eq")) {
            if (value == null) {
                return obj == null;
            }
            return value.equals(obj);
        } else if (key.equals("$ne")) {
            if (value == null) {
                return obj != null;
            }
            return !value.equals(obj);
        }
        switch (key) {
            case "$ne":
                if (value == null) {
                    return obj != null;
                }
                return !value.equals(obj);
            case "$in":
                if (value instanceof Collection) {
                    Collection c = (Collection) value;
                    return c.contains(obj);
                } else {
                    return value.equals(obj);
                }
            case "$nin":
                if (value instanceof Collection) {
                    return !((Collection) value).contains(obj);
                } else {
                    return !value.equals(obj);
                }
            case "$all":
                if (obj instanceof Collection) {
                    if (value instanceof Collection) {
                        return ((Collection) obj).containsAll((Collection) value);
                    } else {
                        return ((Collection) obj).contains(value);
                    }
                } else {
                    return false;
                }
            case "$size":
                Integer v = ConvertUt.convertType(value, Integer.class);
                if (obj instanceof Collection) {
                    return v == ((Collection) obj).size();
                } else {
                    return v == 1;
                }
        }
        //数值比较
        if (obj == null || value == null) {
            return false;
        }
        Double v = ConvertUt.convertType(value, Double.class);
        Double o = ConvertUt.convertType(obj, Double.class);
        switch (key) {
            case "$lt":
                return o < v;
            case "$lte":
                return o <= v;
            case "$gt":
                return o > v;
            case "$gte":
                return o >= v;
            default:
                return false;
        }
    }

}

package com.d2c.common.base.resolver;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.JsonUt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 根据Query查询对象数据
 */
public class QueryResolver {

    private static final Logger logger = LoggerFactory.getLogger(QueryResolver.class);
    private static final String FEILD_SPAC = ".";
    private static final String FEILD_REGEX = "/.";
    private static final String KEY_OPER = "$";

    /**
     * 解析字段表达式, 获取对象中的值
     */
    public static Object getValue(Object bean, String query) {
        Object res = null;
        if (JsonUt.isJson(query)) {
            res = getJsonValue(bean, query);
        } else {
            res = getFieldValue(bean, query);
        }
        return res;
    }

    private static Object getFieldValue(Object bean, String field) {
        Object res = bean;
        if (field.indexOf(FEILD_SPAC) > 0) {
            for (String f : field.split(FEILD_REGEX)) {
                res = BeanUt.getValue(res, f);
            }
        } else {
            res = BeanUt.getValue(bean, field);
        }
        return res;
    }

    private static Object getJsonValue(Object bean, String json) {
        try {
            ObjectNode node = JsonUt.getObjectNode(bean, json);
            return getJsonValueImpl(bean, node);
        } catch (Exception e) {
            logger.error("根据json解析对象失败...json:" + json, e);
        }
        return bean;
    }

    private static Object getJsonValueImpl(Object bean, JsonNode node) throws Exception {
        Object res = null;
        switch (node.getNodeType()) {
            case OBJECT:
                Iterator<Entry<String, JsonNode>> it = node.fields();
                if (!it.hasNext()) break;
                Entry<String, JsonNode> entry = (Entry<String, JsonNode>) it.next();
                res = keyExpr(entry.getKey(), getJsonValueImpl(bean, entry.getValue()));
                break;
            case STRING:
                res = getFieldValue(bean, node.asText());
                break;
            case NUMBER:
                res = node.numberValue();
                break;
            case BOOLEAN:
                res = node.booleanValue();
                break;
            default:
                break;
        }
        return res;
    }

    private static Object keyExpr(String key, Object value) throws Exception {
        if (key.startsWith(KEY_OPER)) {
            key = key.substring(1);
            switch (key) {
                case "count":
                    if (value instanceof Collection) {
                        return new Integer(((Collection<?>) value).size());
                    }
                    break;
                default:
                    break;
            }
        }
        return value;
    }

}

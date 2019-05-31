package com.d2c.common.base.utils;

import com.d2c.common.base.exception.JsonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * JSON转换工具类
 *
 * @author wull
 */

public class JsonUt {

    public final static ObjectMapper jsonMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUt.class);
    private static final String errorMsg = "JSON 解析失败";

    public static String serialize(Object object) {
        try {
            if (object == null) {
                return null;
            }
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            return jsonMapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    /**
     * List, Map 泛型复杂解析方式
     */
    public static <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        try {
            return jsonMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    public static <T> T deserialize(File file, Class<T> valueType) {
        return deserialize(FileUt.read(file), valueType);
    }

    public static <T> T deserialize(File file, TypeReference<T> valueTypeRef) {
        return deserialize(FileUt.read(file), valueTypeRef);
    }

    /**
     * Json转为HashMap
     */
    public static Map<String, ?> deserializeMap(String json) {
        return deserialize(json, new TypeReference<HashMap<String, ?>>() {
        });
    }

    /**
     * 修改JSON中某一项的值
     */
    public static String updateJsonValue(String json, String key, String val) {
        try {
            ObjectNode modelNode = (ObjectNode) jsonMapper.readTree(json);
            modelNode.put(key, val);
            return modelNode.toString();
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    /**
     * 更加新的JSON，修改原JSON中某一项的值
     */
    public static String updateJsonByJson(String json, String key, String jsonValue) {
        try {
            ObjectNode modelNode = (ObjectNode) jsonMapper.readTree(json);
            modelNode.set(key, jsonMapper.readTree(jsonValue));
            return modelNode.toString();
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    /**
     * 简单判断字符串是否为json数据
     */
    public static boolean isJson(String json) {
        return json.startsWith("{") || json.startsWith("[");
    }

    /**
     * 是否为数组json
     */
    public static boolean isJsonList(String json) {
        return json.startsWith("[");
    }

    /**
     * 获取ObjectNode
     */
    public static ObjectNode getObjectNode(Object obj, String json) {
        ObjectNode node = null;
        try {
            if (isJson(json)) {
                node = (ObjectNode) jsonMapper.readTree(json);
            }
        } catch (Exception e) {
            throw new JsonException(errorMsg, e);
        }
        return node;
    }

    /**
     * 根据JSON表达式，递归解析对象到MAP中
     */
    public static Map<String, ?> getFieldMap(Object obj, String json) {
        try {
            ObjectNode node = (ObjectNode) jsonMapper.readTree(json);
            Map<String, Object> map = getFieldMapImpl(null, node, obj, null);
            logger.debug("获得map:" + JsonUt.serialize(map));
            return map;
        } catch (Exception e) {
            logger.error(errorMsg, e);
            throw new JsonException(errorMsg, e);
        }
    }

    private static Map<String, Object> getFieldMapImpl(Map<String, Object> map, ContainerNode<?> node, Object obj, String prefix) throws Exception {
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        if (StringUtils.isEmpty(prefix)) {
            prefix = "";
        } else {
            prefix += ".";
        }
        Iterator<Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Entry<String, JsonNode> entry = (Entry<String, JsonNode>) it.next();
            Object value = BeanUt.getValue(obj, entry.getKey());
            String key = prefix + entry.getKey();
            if (entry.getValue().isContainerNode()) {
                getFieldMapImpl(map, (ContainerNode<?>) entry.getValue(), value, key);
            } else {
                if (value == null) {
                    value = entry.getValue().textValue();
                }
                map.put(key, value);
            }
        }
        return map;
    }

}

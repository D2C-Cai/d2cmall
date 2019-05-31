package com.d2c.common.api.json;

import com.d2c.common.base.exception.JsonException;
import com.d2c.common.base.utils.JsonUt;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface Json extends Serializable {

    public static Json build(String json) {
        if (!JsonUt.isJson(json)) {
            throw new JsonException("json解析失败... json:" + json);
        }
        if (JsonUt.isJsonList(json)) {
            return JsonUt.deserialize(json, JsonList.class);
        } else {
            return JsonUt.deserialize(json, JsonBean.class);
        }
    }

    /**
     * 添加字段
     */
    public Object put(String key, Object v);

    /**
     * 批量Map添加
     */
    public void putAll(Map m);

    /**
     * 获取字段
     */
    public Object get(String key);

    /**
     * 转Map
     */
    public Map toMap();

    /**
     * 删除字段
     */
    public Object removeField(String key);

    /**
     * 是否包含字段
     */
    public boolean containsField(String s);

    /**
     * 字段集合
     */
    public Set<String> keySet();

    /**
     * 转JSON字符串
     */
    public String toJson();

}

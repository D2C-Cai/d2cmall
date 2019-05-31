package com.d2c.common.api.json;

import com.d2c.common.api.json.support.StringIndexSet;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.JsonUt;

import java.util.*;

/**
 * 通用JSON对象
 *
 * @author wull
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonList extends ArrayList<Object> implements Json {

    private static final long serialVersionUID = 5921903596818702487L;

    public static JsonList build(String json) {
        return JsonUt.deserialize(json, JsonList.class);
    }

    /**
     * 添加字段 key 为队列序号, 转正整数，异常为-1
     */
    @Override
    public Object put(final String key, final Object v) {
        return put(ConvertUt.toIntDef(key, -1), v);
    }

    /**
     * 添加队列字段
     */
    public Object put(final int key, final Object value) {
        while (key >= size())
            add(null);
        set(key, value);
        return value;
    }

    @Override
    public void putAll(final Map m) {
        for (Map.Entry entry : (Set<Map.Entry>) m.entrySet()) {
            put(entry.getKey().toString(), entry.getValue());
        }
    }

    public void putAll(JsonList list) {
        addAll(list);
    }

    /**
     * 获取字段值
     */
    public Object get(final String key) {
        int i = ConvertUt.toInt(key);
        if (i < 0)
            return null;
        if (i >= size())
            return null;
        return get(i);
    }

    @Override
    public Object removeField(String key) {
        int i = ConvertUt.toIntDef(key, -1);
        if (i < 0)
            return null;
        if (i >= size())
            return null;
        return remove(i);
    }

    @Override
    public boolean containsField(String key) {
        int i = ConvertUt.toInt(key);
        if (i < 0)
            return false;
        return i >= 0 && i < size();
    }

    @Override
    public Set<String> keySet() {
        return new StringIndexSet(size());
    }

    @Override
    public Map toMap() {
        Map m = new HashMap();
        Iterator i = this.keySet().iterator();
        while (i.hasNext()) {
            Object s = i.next();
            m.put(s, this.get(String.valueOf(s)));
        }
        return m;
    }

    /**
     * 转JSON字符串
     */
    public String toJson() {
        return JsonUt.serialize(this);
    }

    @Override
    public String toString() {
        return toJson();
    }

}

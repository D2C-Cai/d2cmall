package com.d2c.common.api.json;

import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.JsonUt;

import java.util.*;

/**
 * 通用JSON对象
 *
 * @author wull
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonBean extends LinkedHashMap<String, Object> implements Json {

    private static final long serialVersionUID = 4423121012784144100L;

    public JsonBean() {
    }

    public JsonBean(final int size) {
        super(size);
    }

    public JsonBean(String key, Object value) {
        put(key, value);
    }

    public JsonBean(Map m) {
        super(m);
    }

    public static JsonBean build(String json) {
        return JsonUt.deserialize(json, JsonBean.class);
    }

    /**
     * 转 Map
     */
    public Map toMap() {
        return new LinkedHashMap<String, Object>(this);
    }

    /**
     * 删除字段
     */
    public Object removeField(String key) {
        return remove(key);
    }

    /**
     * 是否包括字段
     */
    public boolean containsField(String field) {
        return super.containsKey(field);
    }

    /**
     * 获取字段
     */
    public Object get(String key) {
        return super.get(key);
    }

    /**
     * 获取 int 字段
     */
    public int getInt(String key) {
        return ConvertUt.toInt(get(key));
    }

    /**
     * 获取 int 字段
     *
     * @param def 默认返回
     */
    public int getInt(String key, int def) {
        Object foo = get(key);
        if (foo == null)
            return def;
        return ConvertUt.toInt(foo);
    }

    /**
     * 获取 long 字段
     */
    public long getLong(String key) {
        Object foo = get(key);
        return ((Number) foo).longValue();
    }

    /**
     * 获取 long 字段
     *
     * @param def 默认返回
     */
    public long getLong(String key, long def) {
        Object foo = get(key);
        if (foo == null)
            return def;
        return ((Number) foo).longValue();
    }

    /**
     * 获取 double 字段
     */
    public double getDouble(String key) {
        Object foo = get(key);
        return ((Number) foo).doubleValue();
    }

    /**
     * 获取 double 字段
     *
     * @param def默认返回
     */
    public double getDouble(String key, double def) {
        Object foo = get(key);
        if (foo == null)
            return def;
        return ((Number) foo).doubleValue();
    }

    /**
     * 获取 String
     */
    public String getString(String key) {
        Object foo = get(key);
        if (foo == null)
            return null;
        return foo.toString();
    }

    /**
     * 获取 String
     *
     * @param def默认返回
     */
    public String getString(String key, final String def) {
        Object foo = get(key);
        if (foo == null)
            return def;
        return foo.toString();
    }

    /**
     * 获取 Boolean
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 获取 Boolean
     *
     * @param def默认返回
     */
    public boolean getBoolean(String key, boolean def) {
        Object foo = get(key);
        if (foo == null)
            return def;
        if (foo instanceof Number)
            return ((Number) foo).intValue() > 0;
        if (foo instanceof Boolean)
            return ((Boolean) foo).booleanValue();
        throw new IllegalArgumentException("can't coerce to bool:" + foo.getClass());
    }

    /**
     * 获取 Date
     */
    public Date getDate(final String field) {
        return (Date) get(field);
    }

    /**
     * 获取 Date
     *
     * @param def默认返回
     */
    public Date getDate(final String field, final Date def) {
        final Object foo = get(field);
        return (foo != null) ? (Date) foo : def;
    }

    @Override
    public Object put(String key, Object val) {
        return super.put(key, val);
    }

    @Override
    public void putAll(final Map m) {
        for (Map.Entry entry : (Set<Map.Entry>) m.entrySet()) {
            put(entry.getKey().toString(), entry.getValue());
        }
    }

    /**
     * 添加字段
     */
    public JsonBean append(String key, Object val) {
        put(key, val);
        return this;
    }

    public JsonBean add(String key, Object val) {
        put(key, val);
        return this;
    }

    /**
     * 转JSON字符串
     */
    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return JsonUt.serialize(this);
    }

    /**
     * Compares two documents according to their serialized form, ignoring the
     * order of keys.
     *
     * @param o the document to compare to, which must be an instance of
     *          {@link org.bson.BSONObject}.
     * @return true if the documents have the same serialized form, ignoring key
     * order.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JsonBean)) {
            return false;
        }
        JsonBean other = (JsonBean) o;
        if (!keySet().equals(other.keySet())) {
            return false;
        }
        return JsonUt.serialize(this).equalsIgnoreCase(JsonUt.serialize(other));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(JsonUt.serialize(this).toCharArray());
    }

}

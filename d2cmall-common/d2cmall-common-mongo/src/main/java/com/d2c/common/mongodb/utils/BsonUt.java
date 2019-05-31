package com.d2c.common.mongodb.utils;

import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.GenericUt;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * Bson Document 对象转换工具类
 *
 * @author user
 */
public class BsonUt {

    /**
     * Document转换为对象列表
     */
    public static <T> List<T> toBean(List<Document> docs, Class<T> clz) {
        List<T> list = new ArrayList<T>();
        docs.forEach(doc -> list.add(toBean(doc, clz)));
        return list;
    }

    /**
     * Document转换为对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T toBean(Document doc, Class<T> clz) {
        try {
            AssertUt.notNull(clz);
            if (doc == null) return null;
            T bean = clz.newInstance();
            List<Field> fields = BeanUt.getAllFields(clz);
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                //注解映射
                Column column = field.getAnnotation(Column.class);
                Object val = null;
                if (column != null && column.name() != null) {
                    val = doc.get(column.name());
                } else if ("id".equals(fieldName)) {
                    val = doc.get("_id");
                    if (val == null) {
                        val = doc.get("id");
                    }
                } else {
                    val = doc.get(fieldName);
                }
                if (val == null) {
                    continue;
                } else if (val instanceof Document) {
                    if (Map.class.isAssignableFrom(fieldType)) {
                        //返回类型为Map, 获取value值的泛型
                        Class<?> valClz = GenericUt.getTypeClazz(field, 1);
                        Map dm = new LinkedHashMap<>();
                        Iterator<Entry<String, Object>> it = ((Document) val).entrySet().iterator();
                        while (it.hasNext()) {
                            Entry<String, Object> en = it.next();
                            if (en.getKey().equals("_class")) continue;
                            dm.put(en.getKey(), getBeanValue(en.getValue(), valClz));
                        }
                        val = dm;
                    } else {
                        val = toBean((Document) val, fieldType);
                    }
                } else if (val instanceof Collection) {
                    //返回类型为Collection, 获取value值的泛型
                    Class<?> valClz = GenericUt.getTypeClazz(field);
                    List<Object> list = new ArrayList<>();
                    ((Collection) val).forEach(o -> {
                        list.add(getBeanValue(o, valClz));
                    });
                    val = list;
                } else if (val instanceof MongoCollection) {
                    val = colToList(val, field);
                }
                BeanUt.setValue(bean, fieldName, val);
            }
            return bean;
        } catch (Exception e) {
            throw new BaseException("Document转换为对象失败...", e);
        }
    }

    private static Object getBeanValue(Object value, Class<?> clz) {
        if (BeanUt.isBasic(clz)) {
            return getBasic(value);
        } else {
            return toBean((Document) value, clz);
        }
    }

    /**
     * 将对象转化为Bson文档
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Document toBson(Object bean) {
        try {
            if (bean == null) return null;
            //Document类型处理
            if (bean instanceof Document) {
                return (Document) bean;
            }
            Class<? extends Object> clz = bean.getClass();
            Document doc = new Document();
            if (!BeanUt.isBasic(clz)) {
                doc.append("_class", clz.getName());
            }
            //Map类型处理
            if (bean instanceof Map) {
                ((Map) bean).forEach((k, v) -> {
                    doc.append(k.toString(), getBsonValue(v));
                });
                return doc;
            }
            //Bean对象处理
            List<Field> fields = BeanUt.getAllFields(clz);
            for (Field field : fields) {
                String fieldName = field.getName();
                String key = fieldName;
                //注解映射
                Column column = field.getAnnotation(Column.class);
                Transient trans = field.getAnnotation(Transient.class);
                if (column != null && column.name() != null) {
                    key = column.name();
                } else if (trans != null) {
                    continue;
                } else {
                    if ("id".equals(key)) {
                        key = "_id";
                    }
                }
                Object val = BeanUt.getValue(bean, fieldName);
                if (val != null) {
                    doc.append(key, getBsonValue(val));
                }
            }
            return doc;
        } catch (Exception e) {
            throw new BaseException("对象转Bson失败...", e);
        }
    }

    @SuppressWarnings({"unchecked"})
    private static Object getBsonValue(Object value) {
        if (value instanceof Collection) {
            Collection<Object> c = (Collection<Object>) value;
            Collection<Object> cn = BeanUt.newInstance(c.getClass());
            for (Object o : c) {
                if (o != null) {
                    cn.add(getBsonValue(o));
                }
            }
            return cn;
        } else if (value instanceof Map) {
            return toBson(value);
        } else if (BeanUt.isBasic(value)) {
            return getBasic(value);
        } else {
            return toBson(value);
        }
    }

    /**
     * 基本属性过滤
     */
    private static Object getBasic(Object val) {
        if (val instanceof BigDecimal) {
            return ((BigDecimal) val).doubleValue();
        }
        return val;
    }

    /**
     * 将文档集转化为列表
     */
    private static List<Object> colToList(Object bson, Field field) {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();// 获取列表的类型
        List<Object> objs = new ArrayList<Object>();
        @SuppressWarnings("unchecked")
        MongoCollection<Document> cols = (MongoCollection<Document>) bson;
        MongoCursor<Document> cursor = cols.find().iterator();
        while (cursor.hasNext()) {
            Document child = cursor.next();
            @SuppressWarnings("rawtypes")
            Class clz = (Class) pt.getActualTypeArguments()[0];// 获取元素类型
            @SuppressWarnings("unchecked")
            Object obj = toBean(child, clz);
            objs.add(obj);
        }
        return objs;
    }

}

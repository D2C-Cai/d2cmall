package com.d2c.common.base.utils;

import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.func.Function;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Bean对象处理类
 *
 * @author wull
 */
public class BeanUt {

    public static final List<Class<?>> BASE_TYPE = Arrays.asList(
            String.class, Integer.class, Long.class, Double.class, Boolean.class, Float.class, Short.class,
            Byte.class, Character.class, Date.class, Number.class, BigDecimal.class, BigInteger.class, java.sql.Date.class
    );
    private static final Logger logger = LoggerFactory.getLogger(BeanUt.class);

    static {
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    }

    /**
     * 对象是否为基本类型
     * <p> JAVA基本类是由bootstrap载入的，classloader为null
     */
    public static boolean isBasic(Object obj) {
        return isBasic(obj.getClass());
    }

    public static boolean isBasic(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    /**
     * 是否为普通实体对象
     */
    public static boolean isBean(Object obj) {
        return isBean(obj.getClass());
    }

    public static boolean isBean(Class<?> clz) {
        return !isBasic(clz) && !Iterable.class.isAssignableFrom(clz) && !Map.class.isAssignableFrom(clz);
    }

    /**
     * 对象实例化
     */
    public static <T> T newInstance(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 对象实例化，带有参数
     */
    public static <T> T newInstance(Class<T> clz, Class<?>[] types, Object... params) {
        try {
            return clz.getConstructor(types).newInstance(params);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 获得Class，Class.forName
     */
    public static Class<?> classForName(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 根据类名称，创建默认类
     */
    public static Object buildByClassName(String className) {
        return newInstance(classForName(className));
    }

    /**
     * 对象数据复制并生成实例
     */
    public static <T> T buildBean(Object bean, Class<T> tgClz) {
        try {
            if (bean == null)
                return null;
            return copyProperties(newInstance(tgClz), bean);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 添加非空的对象属性到目标对象， 覆盖目标对象已有数据
     */
    public static <T> T applys(T dest, Object... origs) {
        for (Object orig : origs) {
            apply(dest, orig);
        }
        return dest;
    }

    public static <T> T apply(T dest, Object orig) {
        return applyImpl(dest, orig, true);
    }

    public static <T> T apply(T dest, Object orig, Function<Object, Object> func) {
        return applyImpl(dest, orig, true, func);
    }

    /**
     * 添加非空的对象属性到目标对象， 不覆盖目标已有数据
     */
    public static <T> T applyIf(T dest, Object orig) {
        return applyImpl(dest, orig, false);
    }

    /**
     * 对象数据复制
     *
     * @param dest 目标被修改数据
     * @param orig 属性源
     */
    public static <T> T copyProperties(final T dest, final Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
            return dest;
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    public static <T> T copyProperties(final T dest, final Object... origs) {
        for (Object orig : origs) {
            copyProperties(dest, orig);
        }
        return dest;
    }

    /**
     * 获取对象getter数据
     */
    public static Object getValue(Object bean, String fieldName) {
        try {
            return PropertyUtils.getProperty(bean, fieldName);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (NestedNullException e) {
            return null;
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    public static <T> T getValue(Object bean, String fieldName, Class<T> resClz) {
        return cast(getValue(bean, fieldName), resClz);
    }

    public static Object getSimpleValue(Object bean, String fieldName) {
        try {
            return PropertyUtils.getSimpleProperty(bean, fieldName);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 解析字段表达式, 获取对象中的值 1. "name" 对应字段name的值 2. "user.field" 对象user下的name值
     * 3."{name: defName, user:{name: def}}" 返回 LinkedHashMap: {name: test,
     * user.name: username}
     */
    public static Object getValueExpr(Object bean, String expr) {
        Object res = null;
        if (JsonUt.isJson(expr)) {
            res = JsonUt.getFieldMap(bean, expr);
        } else if (expr.indexOf(".") > 0) {
            res = bean;
            for (String field : expr.split("/.")) {
                res = getValue(res, field);
            }
        } else {
            res = getValue(bean, expr);
        }
        return res;
    }

    public static <T> T getValueExpr(Object bean, String expr, Class<T> outClz) {
        return cast(getValueExpr(bean, expr), outClz);
    }

    /**
     * 设置对象setter数据
     */
    public static void setValue(Object bean, String fieldName, Object fieldValue) {
        try {
            PropertyUtils.setProperty(bean, fieldName, fieldValue);
        } catch (IllegalArgumentException e) {
            try {
                Class<?> setType = PropertyUtils.getPropertyType(bean, fieldName);
                if (!setType.equals(fieldValue.getClass())) {
                    fieldValue = ConvertUt.convertType(fieldValue, setType);
                }
                PropertyUtils.setProperty(bean, fieldName, fieldValue);
            } catch (Exception ex) {
                throw new BaseException(ex);
            }
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 获取全部的Field
     */
    public static List<Field> getAllFields(Class<?> clz) {
        return ReflectUt.getAllFields(clz);
    }

    public static List<Field> getFields(Class<?> clz) {
        return ReflectUt.getFields(clz);
    }

    public static List<Field> getEntryFields(Class<?> beanClass) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clz = beanClass; ReflectUt.isBeanClz(clz); clz = clz.getSuperclass()) {
            fields.addAll(getFields(clz));
            if (!clz.getSuperclass().isAnnotationPresent(Entity.class)) {
                break;
            }
        }
        return fields;
    }

    /**
     * 所有公用（public）方法包括其继承类的公用方法，实现接口的方法
     */
    public static List<Method> getMethods(final Class<?> beanClass) {
        return Arrays.asList(beanClass.getMethods());
    }

    /**
     * 根据注解获取类方法
     */
    public static List<Method> getMethods(final Class<?> cls, final Class<? extends Annotation> annCls) {
        return MethodUtils.getMethodsListWithAnnotation(cls, annCls);
    }

    /**
     * 对象数据转化为Map
     */
    public static Map<String, Object> toMap(final Object obj) {
        try {
            return PropertyUtils.describe(obj);
        } catch (Exception e) {
            throw new AssertException(e);
        }
    }

    /**
     * 对象转换
     */
    public static <T> T cast(Object obj, Class<T> clz) {
        try {
            return clz.cast(obj);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 将Map中的数据填充到对象中
     */
    public static <T> T toObject(Class<T> clazz, Map<String, ?> map) {
        try {
            T obj = clazz.newInstance();
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            throw new AssertException(e);
        }
    }

    public static <T> T toObject(T obj, Map<String, ?> map) {
        try {
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            throw new AssertException(e);
        }
    }

    /**
     * 对象序列化为byte数组
     */
    public static byte[] toByte(Object obj) {
        byte[] bb = null;
        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)) {
            outputStream.writeObject(obj);
            outputStream.flush();
            bb = byteArray.toByteArray();
        } catch (IOException e) {
            throw new BaseException(e);
        }
        return bb;
    }

    /**
     * 字节数组转为Object对象
     */
    public static Object toObject(byte[] bytes) {
        Object readObject = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream inputStream = new ObjectInputStream(in)) {
            readObject = inputStream.readObject();
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return readObject;
    }

    /**
     * 对类中String类型的private成员变量trim
     *
     * @param object
     * @return
     */
    public static Object trimString(Object object) {
        if (object == null) {
            return object;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
                try {
                    // 跳过某些特殊get方法
                    property.getWriteMethod();
                } catch (Exception e) {
                    continue;
                }
                Method setter = property.getWriteMethod();
                try {
                    // 跳过某些特殊get方法
                    setter.getParameterTypes();
                } catch (Exception e) {
                    continue;
                }
                Method getter = property.getReadMethod();
                if (getter == null)
                    continue;
                Class<?> returnType = getter.getReturnType();
                if (getter.invoke(object) != null && returnType.isAssignableFrom(String.class)) {
                    setter.invoke(object, getter.invoke(object).toString().trim());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return object;
    }
    // ******************* private **********************

    /**
     * 添加非空的对象属性到目标对象
     *
     * @param dest   目标对象
     * @param orig   被复制对象
     * @param isOver 是否覆盖目标对象已有数据
     */
    private static <T> T applyImpl(final T dest, final Object orig, final boolean isOver) {
        return applyImpl(dest, orig, isOver, null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T applyImpl(final T dest, final Object orig, final boolean isOver, Function<Object, Object> func) {
        if (dest == null || orig == null) return dest;
        if (dest instanceof Map) {
            Map destMap = (Map) dest;
            if (orig instanceof Map) {
                Map origMap = (Map) orig;
                if (isOver) {
                    destMap.putAll(origMap);
                } else {
                    origMap.forEach((k, v) -> {
                        if (!destMap.containsKey(k)) {
                            destMap.put(k, FuncUt.getValue(v, func));
                        }
                    });
                }
            } else {
                for (PropertyDescriptor origDescriptor : PropertyUtils.getPropertyDescriptors(orig)) {
                    final String name = origDescriptor.getName();
                    if ("class".equals(name)) {
                        continue;
                    }
                    if (PropertyUtils.isReadable(orig, name)) {
                        origDescriptor.getReadMethod().getAnnotation(Transient.class);
                        final Object value = BeanUt.getSimpleValue(orig, name);
                        if (value != null) { // 源目标数据不为空，才复制
                            if (!isOver && destMap.containsKey(name)) {
                                continue;
                            }
                            destMap.put(name, FuncUt.getValue(value, func));
                        }
                    }
                }
            }
        } else {
            if (orig instanceof Map) {
                final Map<String, Object> propMap = (Map<String, Object>) orig;
                propMap.forEach((name, value) -> {
                    if (value != null) { // 源目标数据不为空，才复制
                        if ("_id".equals(name)) {
                            name = "id";
                        }
                        if (PropertyUtils.isWriteable(dest, name)) {
                            if (!isOver && PropertyUtils.isReadable(dest, name)) {
                                try {
                                    Object destValue = BeanUt.getSimpleValue(dest, name);
                                    if (destValue != null) {
                                        return;
                                    }
                                } catch (Exception e) {
                                    return;
                                }
                            }
                            BeanUt.setValue(dest, name, FuncUt.getValue(value, func));
                        }
                    }
                });
            } else {
                // JavaBean
                for (PropertyDescriptor origDescriptor : PropertyUtils.getPropertyDescriptors(orig)) {
                    final String name = origDescriptor.getName();
                    if ("class".equals(name)) {
                        continue;
                    }
                    if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                        final Object value = BeanUt.getSimpleValue(orig, name);
                        if (value != null) { // 源目标数据不为空，才复制
                            if (!isOver && PropertyUtils.isReadable(dest, name)) {
                                final Object destValue = BeanUt.getSimpleValue(dest, name);
                                if (destValue != null) {
                                    continue;
                                }
                            }
                            BeanUt.setValue(dest, name, FuncUt.getValue(value, func));
                        }
                    }
                }
            }
        }
        return dest;
    }

}

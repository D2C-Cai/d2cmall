package com.d2c.common.base.utils;

import com.d2c.common.base.exception.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * 反射工具类.
 *
 * @author wull
 */
public class ReflectUt {

    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    private static Logger logger = LoggerFactory.getLogger(ReflectUt.class);

    /**
     * 是否为bean POJO基本类型
     * <p>
     */
    public static boolean isBeanClz(Class<?> clz) {
        return !notBeanClz(clz);
    }

    public static boolean notBeanClz(Class<?> clz) {
        return clz == null || Object.class.equals(clz) || Map.class.isAssignableFrom(clz) || Collection.class.isAssignableFrom(clz);
    }
    //******************* Method **********************

    /**
     * 所有公用（public）方法包括其继承类的公用方法，实现接口的方法
     */
    public static List<Method> getMethods(final Class<?> beanClass) {
        return Arrays.asList(beanClass.getMethods());
    }

    /**
     * 所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
     * 也包括它所实现接口的方法
     */
    public static List<Method> getDeclaredMethods(final Class<?> beanClass) {
        return Arrays.asList(beanClass.getDeclaredMethods());
    }

    /**
     * 所有方法，包括公共、保护、默认（包）访问和私有方法，也包括接口
     */
    public static List<Method> getAllMethods(final Class<?> beanClass) {
        List<Method> mds = new ArrayList<>();
        for (Class<?> clz = beanClass; clz != Object.class; clz = clz.getSuperclass()) {
            mds.addAll(getDeclaredMethods(clz));
        }
        return mds;
    }

    /**
     * 调用Getter方法.
     */
    public static Object getValue(Object bean, String fieldName) {
        return BeanUt.getValue(bean, fieldName);
    }

    public static Object invokeGetterMethod(Object bean, String fieldName) {
        return invokeMethod(bean, getGetterName(fieldName), new Class[]{}, new Object[]{});
    }

    public static Method getGetterMethod(Object bean, String fieldName) {
        return getAccessibleMethod(bean, getGetterName(fieldName));
    }

    public static String getGetterName(String fieldName) {
        return "get" + StringUt.upperFrist(fieldName);
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void setValue(Object bean, String fieldName, Object value) {
        BeanUt.setValue(bean, fieldName, value);
    }

    public static void invokeSetterMethod(Object bean, String propertyName, Object value) {
        invokeSetterMethod(bean, propertyName, value, value.getClass());
    }

    public static void invokeSetterMethod(Object bean, String propertyName, Object value, Class<?> propertyType) {
        invokeMethod(bean, getSetterName(propertyName), new Class[]{propertyType}, new Object[]{value});
    }

    public static Method getSetterMethod(Object bean, String propertyName, Class<?> parameterType) {
        return getAccessibleMethod(bean, getSetterName(propertyName), new Class[]{parameterType});
    }

    public static String getSetterName(String propertyName) {
        return "set" + StringUt.upperFrist(propertyName);
    }
    //***************** Field *******************

    /**
     * 获得Field所有字段
     */
    public static List<Field> getAllFields(final Class<?> beanClass) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clz = beanClass; isBeanClz(clz); clz = clz.getSuperclass()) {
            fields.addAll(getFields(clz));
        }
        return fields;
    }

    /**
     * 获得Field类字段, 不包括继承类
     */
    public static List<Field> getFields(final Class<?> beanClass) {
        List<Field> fields = new ArrayList<>();
        for (Field field : beanClass.getDeclaredFields()) {
            // 排除静态字段
            if (!Modifier.isStatic(field.getModifiers())) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object bean, final String fieldName) {
        Field field = getAccessibleField(bean, fieldName);
        if (field == null) {
            throw new ReflectException("对象属性不存在...field: " + bean + "." + fieldName);
        }
        Object result = null;
        try {
            result = field.get(bean);
        } catch (Exception e) {
            logger.error("读取对象属性失败...field: " + bean + "." + fieldName, e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object bean, final String fieldName, final Object value) {
        Field field = getAccessibleField(bean, fieldName);
        if (field == null) {
            throw new ReflectException("对象属性不存在...field: " + bean + "." + fieldName);
        }
        try {
            field.set(bean, value);
        } catch (Exception e) {
            logger.error("设置对象属性失败...field: " + bean + "." + fieldName, e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * <p>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        AssertUt.notNull(obj);
        AssertUt.notEmpty(fieldName);
        for (Class<?> clz = obj.getClass(); clz != Object.class; clz = clz.getSuperclass()) {
            try {
                Field field = clz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
     */
    public static Object invokeMethod(final Object bean, final String methodName) {
        return invokeMethod(bean, methodName, new Class[]{}, new Object[]{});
    }

    public static Object invokeMethod(final Object bean, final String methodName, final Class<?> parameterType, final Object arg) {
        return invokeMethod(bean, methodName, new Class[]{parameterType}, new Object[]{arg});
    }

    public static Object invokeMethod(final Object bean, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(bean, methodName, parameterTypes);
        if (method == null) {
            throw new ReflectException("方法不存在... " + bean + "." + methodName);
        }
        try {
            return method.invoke(bean, args);
        } catch (Exception e) {
            throw new ReflectException("Reflect反射异常...", e);
        }
    }

    public static Object invokeMethod(final Object bean, final Method method, final Object... args) {
        try {
            return method.invoke(bean, args);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
     * args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        AssertUt.notNull(obj);
        for (Class<?> clz = obj.getClass(); clz != Object.class; clz = clz.getSuperclass()) {
            try {
                Method method = clz.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
     * extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be
     * determined
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    public static void setFieldValue(final Object bean, final Field field, final Object value) {
        boolean isAccessible = field.isAccessible();
        try {
            if (!isAccessible) {
                field.setAccessible(true);
            }
            try {
                field.set(bean, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        } finally {
            if (!isAccessible) {
                field.setAccessible(isAccessible);
            }
        }
    }

    public static Object[] getNullArgs(final Method method) {
        int size = method.getParameterTypes().length;
        return new Object[size];
    }
    // ***************** cglib *******************

    /**
     * 对于被cglib AOP过的对象, 取得真实的Class类型.
     */
    public static Class<?> getUserClass(Class<?> clazz) {
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

}

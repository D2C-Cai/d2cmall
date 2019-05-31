package com.d2c.common.base.utils;

import com.d2c.common.base.func.Function;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.util.*;

/**
 * 列表工具类
 *
 * @author wull
 */
public class ListUt {

    public static boolean isEmpty(Object[] objs) {
        return (objs == null || objs.length <= 0);
    }

    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean notEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static <T> T getFrist(List<T> list) {
        if (notEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public static <T, R> List<R> forEachMap(Iterable<T> its, Class<R> resType, Function<T, R> func) {
        AssertUt.notNull(func);
        List<R> list = new ArrayList<R>();
        its.forEach(it -> list.add(func.call(it)));
        return list;
    }

    public static <T, E> List<E> getValueExpr(Iterable<T> its, String expr, Class<E> outClz) {
        List<E> list = new ArrayList<E>();
        its.forEach(it -> {
            list.add(BeanUt.getValueExpr(it, expr, outClz));
        });
        return list;
    }

    /**
     * 列表对象转换
     */
    public static <T> List<T> cast(List<?> list, Class<T> clz) {
        List<T> resList = new ArrayList<T>();
        if (list != null) {
            list.forEach(obj -> {
                try {
                    resList.add(BeanUt.cast(obj, clz));
                } catch (Exception e) {
                }
            });
        }
        return resList;
    }

    /**
     * 去重，独立不重复对象
     */
    public static <T> List<T> distinct(List<T> list) {
        return new ArrayList<>(new HashSet<>(list));
    }

    /**
     * 依据某个字段对集合进行排序, 并取前几条数据
     *
     * @param list      待排序的集合
     * @param fieldName 依据这个字段进行排序
     * @param asc       如果为true，是正序；为false，为倒序
     */
    public static <T> List<T> sort(List<T> list, String fieldName, boolean asc, int limit) {
        sort(list, fieldName, asc);
        if (limit < list.size()) {
            list = list.subList(0, limit);
        }
        return list;
    }

    public static <T> List<T> sort(List<T> list, String fieldName, boolean asc) {
        Comparator<?> mycmp = ComparableComparator.getInstance();
        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
        if (!asc) {
            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
        }
        Collections.sort(list, new BeanComparator<T>(fieldName, mycmp));
        return list;
    }

    /**
     * 根据列表转换为Map
     */
    public static <K, V> Map<K, V> listToMap(List<?> list, String keyField, String valueField, Class<K> keyClz, Class<V> valueClz) {
        Map<K, V> map = new HashMap<>();
        list.forEach(bean -> {
            map.put(keyClz.cast(BeanUt.getValue(bean, keyField)), valueClz.cast(BeanUt.getValue(bean, valueField)));
        });
        return map;
    }

    /**
     * 对象分组
     * <p>根据对象对应字段分组
     *
     * @param list     对象列表
     * @param keyField 分组字段名称
     * @return 分组后对象Map
     */
    public static <T> Map<String, List<T>> groupToMap(List<T> list, String keyField) {
        return groupToMap(list, keyField, String.class);
    }

    public static <K, T> Map<K, List<T>> groupToMap(List<T> list, String keyField, Class<K> keyClz) {
        Map<K, List<T>> map = new HashMap<>();
        list.forEach(bean -> {
            K key = BeanUt.getValue(bean, keyField, keyClz);
            if (key == null) return;
            List<T> array = map.get(key);
            if (array == null) {
                array = new ArrayList<T>();
                map.put(key, array);
            }
            array.add(bean);
        });
        return map;
    }

    /**
     * 对象分组，返回对应字段
     *
     * @param list     对象列表
     * @param keyField 分组字段名称
     * @param keyClz   key类型
     * @param valueClz 返回集合数据类型
     * @return 分组后对象Map
     */
    public static <K, V> Map<K, List<V>> groupToMap(List<?> list, String keyField, Class<K> keyClz, Class<V> valueClz) {
        return groupToMap(list, keyField, null, keyClz, valueClz);
    }

    /**
     * 对象分组，返回对应字段
     *
     * @param list       对象列表
     * @param keyField   分组字段名称
     * @param valueField 返回字段名称 (可为null，为null即整个类转换)
     * @param keyClz     key类型
     * @param valueClz   返回集合数据类型
     * @return 分组后对象Map
     */
    public static <K, V> Map<K, List<V>> groupToMap(List<?> list, String keyField, String valueField, Class<K> keyClz, Class<V> valueClz) {
        Map<K, List<V>> map = new HashMap<>();
        list.forEach(bean -> {
            K key = BeanUt.getValue(bean, keyField, keyClz);
            if (key == null) return;
            List<V> array = map.get(key);
            if (array == null) {
                array = new ArrayList<V>();
                map.put(key, array);
            }
            if (valueField == null) {
                array.add(BeanUt.buildBean(bean, valueClz));
            } else {
                array.add(BeanUt.getValue(bean, valueField, valueClz));
            }
        });
        return map;
    }

}

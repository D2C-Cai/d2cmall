package com.d2c.common.api.convert;

import com.d2c.common.api.annotation.HideResolver;
import com.d2c.common.api.json.JsonBean;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.ReflectUt;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实例转换方法
 *
 * @author wull
 */
public class ConvertHelper {

    protected static final Logger logger = LoggerFactory.getLogger(ConvertHelper.class);

    public static <T> List<T> convertList(Iterable<T> its) {
        List<T> list = new ArrayList<>();
        its.forEach(it -> list.add(convert(it)));
        return list;
    }

    public static <T, V> List<V> convertList(Iterable<T> its, Class<V> resClz) {
        List<V> list = new ArrayList<>();
        its.forEach(it -> list.add(convert(it, resClz)));
        return list;
    }

    public static JsonBean convertJson(Object bean) {
        return convert(bean, JsonBean.class);
    }

    public static <V> V convert(V bean) {
        return convert(bean, bean);
    }

    public static <V> V convert(Object bean, Class<V> resClz) {
        AssertUt.notNull(resClz);
        return ConvertHelper.convert(bean, BeanUt.newInstance(resClz));
    }

    /**
     * 对象转换
     * <p>
     * 尝试调用实体类中 convert(Object bean) 转换方法
     */
    @SuppressWarnings("unchecked")
    public static <V> V convert(Object bean, V view) {
        try {
            if (BeanUt.isBean(view)) {
                Method method = ReflectUt.getAccessibleMethod(view, "convert", bean.getClass());
                if (method == null)
                    throw new BaseException();
                view = convertSimple(bean, view);
                view = (V) ReflectUt.invokeMethod(view, method, bean);
            }
        } catch (Exception e) {
            view = convertSimple(bean, view);
        }
        return view;
    }

    /**
     * 转换实现方法
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <V> V convertSimple(final Object bean, final V view) {
        if (view == null || bean == null)
            return view;
        if (bean.equals(view) || bean.getClass().equals(view.getClass())) {
            return HideResolver.hideOnBean(view);
        } else if (view instanceof Map) {
            Map vmap = (Map) view;
            if (bean instanceof Map) {
                vmap.putAll((Map) bean);
            } else {
                List<Field> fields = BeanUt.getAllFields(bean.getClass());
                for (Field field : fields) {
                    final String name = field.getName();
                    if (PropertyUtils.isReadable(bean, name)) {
                        Object value = BeanUt.getValue(bean, name);
                        // 字符串字段检查隐藏标签，隐藏相应字段
                        value = HideResolver.hideValue(field, value);
                        vmap.put(name, value);
                    }
                }
            }
        } else {
            if (bean instanceof Map) {
                ((Map<String, Object>) bean).forEach((name, value) -> {
                    if (PropertyUtils.isWriteable(view, name)) {
                        BeanUt.setValue(view, name, value);
                    }
                });
            } else {
                List<Field> fields = BeanUt.getAllFields(view.getClass());
                for (Field field : fields) {
                    final String name = field.getName();
                    if (PropertyUtils.isReadable(view, name) && PropertyUtils.isWriteable(bean, name)) {
                        Object value = BeanUt.getValue(bean, name);
                        // 字符串字段检查隐藏标签，隐藏相应字段
                        value = HideResolver.hideValue(field, value);
                        BeanUt.setValue(view, name, value);
                    }
                }
            }
        }
        return view;
    }

}

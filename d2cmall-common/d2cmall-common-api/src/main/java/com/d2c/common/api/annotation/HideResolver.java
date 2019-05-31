package com.d2c.common.api.annotation;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.StringUt;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 隐藏字段解析方法
 *
 * @author wull
 */
public class HideResolver {

    /**
     * 隐藏字段部分数据
     */
    public static Object hideValue(final Field field, final Object value) {
        if (value == null) {
            return null;
        }
        if (field.getType().equals(String.class)) {
            HideColumn column = field.getAnnotation(HideColumn.class);
            if (column != null) {
                if (column.start() > 0 && column.end() > 0) {
                    return StringUt.hideValue(value.toString(), column.start(), column.end());
                }
                if (column.type() != null) {
                    return column.type().hide(value.toString());
                }
            }
        }
        return value;
    }

    /**
     * 检查整个对象, 隐藏数据
     * <p>
     * 注解@HideColumn
     */
    public static <T> T hideOnBean(T bean) {
        List<Field> fields = BeanUt.getAllFields(bean.getClass());
        for (Field field : fields) {
            final String name = field.getName();
            if (field.getType().equals(String.class)) {
                HideColumn column = field.getAnnotation(HideColumn.class);
                if (column != null) {
                    Object value = BeanUt.getValue(bean, name);
                    if (value != null) {
                        if (column.start() > 0 && column.end() > 0) {
                            value = StringUt.hideValue(value.toString(), column.start(), column.end());
                            BeanUt.setValue(bean, name, value);
                        } else if (column.type() != null) {
                            value = column.type().hide(value.toString());
                            BeanUt.setValue(bean, name, value);
                        }
                    }
                }
            }
        }
        return bean;
    }

}

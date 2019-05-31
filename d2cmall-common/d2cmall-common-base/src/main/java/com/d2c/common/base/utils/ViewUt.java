package com.d2c.common.base.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Bean对象处理类
 *
 * @author wull
 */
public class ViewUt {

    /**
     * 重置对象属性默认值
     */
    public static <T> T setDefault(T bean) {
        if (BeanUt.isBean(bean)) {
            List<Field> fields = BeanUt.getAllFields(bean.getClass());
            for (Field field : fields) {
                final String name = field.getName();
                if (PropertyUtils.isReadable(bean, name)) {
                    Object value = BeanUt.getValue(bean, name);
                    if (value == null) {
                        Class<?> fieldType = field.getType();
                        if (String.class.isAssignableFrom(fieldType)) {
                            BeanUt.setValue(bean, name, "");
                        } else if (Number.class.isAssignableFrom(fieldType)) {
                            BeanUt.setValue(bean, name, 0);
                        } else if (Boolean.class.isAssignableFrom(fieldType)) {
                            BeanUt.setValue(bean, name, false);
                        }
                    }
                }
            }
        }
        return bean;
    }

}

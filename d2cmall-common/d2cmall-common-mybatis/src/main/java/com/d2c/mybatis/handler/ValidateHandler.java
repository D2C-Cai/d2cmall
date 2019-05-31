package com.d2c.mybatis.handler;

import com.alibaba.druid.util.StringUtils;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.utils.BeanUt;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ValidateHandler {

    /**
     * 校验对象
     *
     * @AssertColumn 注释对象字段和方法
     * AssertException 抛出校验异常
     */
    public void check(Object bean) {
        for (Field field : BeanUt.getEntryFields(bean.getClass())) {
            AssertColumn ann = field.getAnnotation(AssertColumn.class);
            if (ann != null) {
                ann.type().check(ann, bean, field.getName());
            }
        }
        for (Method method : bean.getClass().getMethods()) {
            AssertColumn ann = method.getAnnotation(AssertColumn.class);
            if (ann != null) {
                try {
                    Object res = method.invoke(bean);
                    if (res != null) {
                        if (res instanceof String) {
                            String str = res.toString();
                            if (!StringUtils.isEmpty(str)) {
                                throw new AssertException(str);
                            }
                        } else if (res instanceof Boolean) {
                            if (!(Boolean) res) {
                                throwMessage(ann);
                            }
                        }
                    }
                } catch (InvocationTargetException e) {
                    Throwable t = e.getTargetException();
                    if (t instanceof AssertException) {
                        throw (AssertException) t;
                    } else {
                        throw new AssertException(t);
                    }
                } catch (AssertException e) {
                    throw e;
                } catch (Exception e) {
                    throw new AssertException(e);
                }
            }
        }
    }

    private void throwMessage(AssertColumn ann) {
        throwMessage(null, ann);
    }

    private void throwMessage(String fieldName, AssertColumn ann) {
        String msg = ann.value();
        if (StringUtils.isEmpty(msg)) {
            if (fieldName != null) {
                msg = fieldName;
            }
            msg = msg + ann.type().getMessage();
        }
        throw new AssertException(msg);
    }

}

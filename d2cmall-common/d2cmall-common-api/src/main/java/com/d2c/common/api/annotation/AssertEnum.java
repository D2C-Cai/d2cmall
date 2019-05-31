package com.d2c.common.api.annotation;

import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.ConvertUt;
import org.apache.commons.lang3.StringUtils;

public enum AssertEnum {
    NOT_NULL("不能为空");
    private String message;

    AssertEnum() {
    }

    AssertEnum(String message) {
        this.message = message;
    }

    /**
     * 校验数据, 通过规则为TRUE
     */
    public String check(AssertColumn ann, Object bean, String fieldName) {
        try {
            Object value = BeanUt.getValue(bean, fieldName);
            checkInit(ann, value);
            checkValue(ann, value);
            return null;
        } catch (AssertException e) {
            String msg = ann.value();
            if (StringUtils.isEmpty(msg)) {
                msg = fieldName + e.getMessage();
            }
            throw new AssertException(msg, e);
        }
    }

    /**
     * 通用校验数据
     */
    void checkInit(AssertColumn ann, Object value) {
        if (value == null || "".equals(value.toString().trim())) {
            if (!ann.nullable()) {
                throw new AssertException("不能为空");
            }
            return;
        }
        checkNumber(ann, value);
        checkString(ann, value);
    }

    boolean checkValue(AssertColumn ann, Object obj) {
        return true;
    }

    /**
     * 校验字符串
     */
    void checkString(AssertColumn ann, Object value) {
        if (value instanceof String) {
            String str = value.toString();
            if (StringUtils.isEmpty(str)) {
                throw new AssertException("不能为空");
            }
            if (ann.min() >= 0 && str.length() <= ann.min()) {
                throw new AssertException("字符长度不能小于" + ann.min());
            }
            if (ann.mineq() >= 0 && str.length() < ann.mineq()) {
                throw new AssertException("字符长度不能小于等于" + ann.mineq());
            }
            if (ann.max() >= 0 && str.length() >= ann.max()) {
                throw new AssertException("字符长度不能大于" + ann.max());
            }
            if (ann.maxeq() >= 0 && str.length() > ann.maxeq()) {
                throw new AssertException("字符长度不能大于等于" + ann.maxeq());
            }
        }
    }

    /**
     * 校验数值类型
     */
    void checkNumber(AssertColumn ann, Object value) {
        if (value instanceof Number) {
            Double v = ConvertUt.convertType(value, Double.class);
            if (ann.min() >= 0 && v <= ann.min()) {
                throw new AssertException("数值不能小于等于" + ann.min());
            }
            if (ann.mineq() >= 0 && v < ann.mineq()) {
                throw new AssertException("数值不能小于" + ann.mineq());
            }
            if (ann.max() >= 0 && v >= ann.max()) {
                throw new AssertException("数值不能大于等于" + ann.max());
            }
            if (ann.maxeq() >= 0 && v > ann.maxeq()) {
                throw new AssertException("数值不能大于" + ann.maxeq());
            }
        }
    }
    // *****************************************************

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

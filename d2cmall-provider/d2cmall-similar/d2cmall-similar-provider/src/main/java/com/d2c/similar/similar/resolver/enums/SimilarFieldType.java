package com.d2c.similar.similar.resolver.enums;

import com.d2c.common.base.utils.DateUt;

import java.math.BigDecimal;
import java.util.Date;

public enum SimilarFieldType {
    STRING("字符串"),
    NUMBER("数值") {
        public double getFieldValue(Object value) {
            return (double) value;
        }
    },
    DECIMAL("高精数值") {
        public double getFieldValue(Object value) {
            BigDecimal v = (BigDecimal) value;
            return v.doubleValue();
        }
    },
    DATE("日期") {
        public double getFieldValue(Object value) {
            return DateUt.toDayOfYear((Date) value);
        }
    },
    TIME("时间") {
        public double getFieldValue(Object value) {
            return DateUt.toSecond((Date) value);
        }
    };
    String remark;

    SimilarFieldType(String remark) {
        this.remark = remark;
    }

    public double getFieldValue(Object value) {
        return 0;
    }

    @Override
    public String toString() {
        return this.remark;
    }
}

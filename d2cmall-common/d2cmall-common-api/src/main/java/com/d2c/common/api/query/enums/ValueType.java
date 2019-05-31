package com.d2c.common.api.query.enums;

import com.d2c.common.base.utils.DateUt;

public enum ValueType {
    STRING,
    LONG {
        Object getValueImpl(String str) {
            return Long.parseLong(str);
        }
    },
    INTEGER {
        Object getValueImpl(String str) {
            return Integer.parseInt(str);
        }
    },
    DOUBLE {
        Object getValueImpl(String str) {
            return Double.parseDouble(str);
        }
    },
    VDATE {
        Object getValueImpl(String str) {
            return DateUt.str2date(str);
        }
    },
    VDATETIME {
        Object getValueImpl(String str) {
            return DateUt.str2minute(str);
        }
    },
    VBOOLEAN {
        Object getValueImpl(String str) {
            return new Boolean(str);
        }
    };

    /**
     * 根据类型返回对应值
     */
    Object getValueImpl(String str) {
        return str;
    }

    public Object getValue(Object obj) {
        try {
            if (obj != null && obj instanceof String) {
                return getValueImpl((String) obj);
            } else {
                return obj;
            }
        } catch (Exception e) {
            return null;
        }
    }
}

package com.d2c.common.api.convert;

import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.StringUt;

import java.util.Arrays;
import java.util.List;

public enum ViewFilterEnum implements SetFilter {
    HideMobile("loginCode", "alipay", "mobile", "buyerCode") {
        Object filterImpl(String fieldName, Object value) {
            return StringUt.hideValue(value.toString(), 3, 8);
        }
    },
    HideBankSn("bankSn") {
        Object filterImpl(String fieldName, Object value) {
            return StringUt.hideValue(value.toString(), 3, 13);
        }
    },
    HideIDCard("identityCard", "licenseNum") {
        Object filterImpl(String fieldName, Object value) {
            return StringUt.hideValue(value.toString(), 4, 14);
        }
    },
    HideRealName("realName") {
        Object filterImpl(String fieldName, Object value) {
            String str = value.toString();
            return StringUt.hideValue(str, 0, str.length() - 1);
        }
//	},
//	DateFormat(Date.class){
//		Object filterImpl(String fieldName, Object value) {
//			return DateUt.second2str((Date) value);
//		}
    };
    List<String> fields;
    Class<?> fieldType;

    ViewFilterEnum(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    ViewFilterEnum(String... fields) {
        this.fields = Arrays.asList(fields);
    }

    @Override
    public Object filter(String fieldName, Object value) {
        AssertUt.notNull(value);
        if (fieldType != null && fieldType.isAssignableFrom(value.getClass())) {
            value = filterImpl(fieldName, value);
        } else if (fields != null && fields.contains(fieldName)) {
            value = filterImpl(fieldName, value);
        }
        return value;
    }

    Object filterImpl(String fieldName, Object value) {
        return value;
    }
}

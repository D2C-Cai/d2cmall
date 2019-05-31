package com.d2c.common.api.annotation;

import com.d2c.common.base.utils.StringUt;
import org.apache.commons.lang3.StringUtils;

/**
 * 隐藏类型枚举
 *
 * @author wull
 */
public enum HideEnum {
    MOBILE {
        public String hide(String value) {
            if (!StringUtils.isNumeric(value)) {
                return value;
            }
            return StringUt.hideValue(value, 3, 8);
        }
    },
    BANKSN {
        public String hide(String value) {
            return StringUt.hideValue(value, 3, 13);
        }
    },
    IDCARD {
        public String hide(String value) {
            return StringUt.hideValue(value, 4, 14);
        }
    },
    LICENSENUM {
        public String hide(String value) {
            return StringUt.hideValue(value, 4, 14);
        }
    },
    REALNAME {
        public String hide(String value) {
            return StringUt.hideValue(value, 0, value.length() - 1);
        }
    };

    public String hide(String value) {
        return value;
    }
}

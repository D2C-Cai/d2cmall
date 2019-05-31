package com.d2c.common.base.utils;

import com.d2c.common.base.exception.AssertException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 断言工具类 (数据校验类)
 *
 * @author wull
 */
public class AssertUt {

    /**
     * 对象不能为空
     */
    public static void notNull(Object obj) {
        notNull(obj, "对象不能为空");
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new AssertException(message);
        }
    }

    public static void isTrue(Boolean expression, String message) {
        if (expression == null || !expression) {
            throw new AssertException(message);
        }
    }

    /**
     * 对象必须为空
     */
    public static void isNull(Object obj) {
        notNull(obj, "对象必须为空");
    }

    public static void isNull(Object obj, String message) {
        if (obj != null) {
            throw new AssertException(message);
        }
    }

    /**
     * 对象必须相同
     */
    public static void isEquals(Object first, Object second) {
        isEquals(first, second, "对象不相同");
    }

    public static void isEquals(Object first, Object second, String message) {
        notNull(first);
        if (!first.equals(second)) {
            throw new AssertException(message);
        }
    }

    /**
     * 字符串不能为空
     */
    public static void notEmpty(String str) {
        notEmpty(str, "字符串不能为空");
    }

    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new AssertException(message);
        }
    }

    public static void notBlank(String str) {
        notBlank(str, "字符串不能为空");
    }

    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new AssertException(message);
        }
    }

    /**
     * 对象是否可转换为数值
     */
    public static void isNumber(String str) {
        isNumber(str, "对象不能换为数值");
    }

    public static void isNumber(String obj, String message) {
        if (!MathUt.isNumber(obj)) {
            throw new AssertException(message);
        }
    }

    /**
     * 电话号码格式
     */
    public static void isPhone(String obj) {
        isPhone(obj, "电话号码格式不正确...");
    }

    public static void isPhone(String str, String message) {
        notNull(str, message);
        if (!match("1[0-9]{10}", str)) {
            throw new AssertException(message);
        }
    }

    /**
     * 钱包密码校验 8-20位数字和字母组合
     */
    public static void isPassword(String password) {
        isPassword(password, "密码必须是8-20位字母，数字或符号(除空格)，且字母，数字，和标点符号至少包含2种");
    }

    public static void isPassword(String password, String message) {
        notBlank(password, message);
        if (password.length() < 8 || password.length() > 20) {
            throw new AssertException(message);
        }
        if (!match(
                "^(?![\\d]+$)(?![a-zA-Z]+$)(?![\\x21-\\x7e][^A-Za-z0-9]+$)[\\da-zA-Z[\\x21-\\x7e][^A-Za-z0-9]]{8,20}$",
                password)) {
            throw new AssertException(message);
        }
    }

    /**
     * 验证邮箱地址
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return match("^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
    }
    // ********************* Collection *******************************

    /**
     * 对象只能唯一
     */
    public static void isOnlyOne(Collection<?> c) {
        isOnlyOne(c, "对象只能唯一");
    }

    public static void isOnlyOne(Collection<?> c, String message) {
        if (c != null && c.size() > 1) {
            throw new AssertException(message);
        }
    }

    /**
     * 列表中对象不能为空
     */
    public static void notEmpty(Collection<?> c) {
        notEmpty(c, "列表中对象不能为空");
    }

    public static void notEmpty(Collection<?> c, String message) {
        notNull(c, message);
        if (c.isEmpty()) {
            throw new AssertException(message);
        }
    }

    /**
     * 正则表达式匹配
     *
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        return Pattern.compile(regex).matcher(str).matches();
    }

}

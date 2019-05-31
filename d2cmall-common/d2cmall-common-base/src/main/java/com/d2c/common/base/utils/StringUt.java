package com.d2c.common.base.utils;

import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.func.Function;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String字符串工具类
 *
 * @author wull
 */
public class StringUt {

    /**
     * 集合字符中间添加间隔符
     */
    public static String join(final String separator, Object... strs) {
        return join(Arrays.asList(strs), separator);
    }

    public static String join(final Object[] array, final String separator) {
        return join(Arrays.asList(array), separator);
    }

    public static String join(final Iterable<?> list, String separator) {
        return join(list, separator, null);
    }

    @SuppressWarnings("rawtypes")
    public static <T> String join(final Iterable<T> list, String separator, Function<T, String> func) {
        if (list == null)
            return null;
        if (separator == null)
            separator = "";
        String res = "";
        for (T o : list) {
            if (o != null) {
                if (StringUtils.isNotBlank(res)) {
                    res += separator;
                }
                if (o instanceof Iterable) {
                    res += "[" + join((Iterable) o, separator) + "]";
                } else if (o.getClass().isArray()) {
                    res += "[" + join((Object[]) o, separator) + "]";
                } else {
                    String s = (func != null ? func.call(o) : String.valueOf(o));
                    if (StringUt.isBlank(s)) {
                        res = StringUtils.removeEnd(res, separator);
                    } else {
                        res += s;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 首字母大写
     */
    public static String upperFrist(String str) {
        return StringUtils.capitalize(str);
    }

    /**
     * 首字母小写
     */
    public static String lowerFrist(String str) {
        return StringUtils.uncapitalize(str);
    }

    /**
     * 大写
     */
    public static String toUpperCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.toUpperCase();
    }

    /**
     * 小写
     */
    public static String toLowerCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.toLowerCase();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 字符串比较，忽略大小写
     */
    public static boolean equalsIgnoreCase(final CharSequence str1, final CharSequence str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * HtmlURL编码解码
     */
    public static String decodeURL(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaseException(e);
        }
    }

    /**
     * HtmlURL编码
     */
    public static String encodeURL(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 是否为空字符串
     */
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    /**
     * UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * nextInt
     */
    public static String nextInt(int bound) {
        return String.valueOf(RandomUt.nextInt(bound));
    }

    /**
     * 是否有中文字符
     */
    public static boolean isChinese(String str) {
        return matchFind("[\u4e00-\u9fa5]", str);
    }

    /**
     * 正则表达式匹配
     *
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        return Pattern.compile(regex).matcher(str).matches();
    }

    /**
     * 正则表达式匹配, 字符匹配
     */
    public static boolean matchFind(String regex, String str) {
        AssertUt.notNull(regex, "正则表达式不能为空!");
        return Pattern.compile(regex).matcher(str).find();
    }
    //

    /**
     * 隐藏字符
     */
    public static String hideMobile(String value) {
        if (!StringUtils.isNumeric(value)) {
            return value;
        }
        return hideValue(value, 3, 8);
    }

    public static String hideBankSn(String value) {
        return hideValue(value, 3, 13);
    }

    public static String hideIDCard(String value) {
        return hideValue(value, 4, 14);
    }

    public static String hideLicenseNum(String value) {
        return hideValue(value, 4, 14);
    }

    public static String hideRealName(String value) {
        return hideValue(value, 0, value.length() - 1);
    }

    public static String hideValue(String value, int start) {
        return hideValue(value, start, -1);
    }

    /**
     * 隐藏字符串
     */
    public static String hideValue(String value, int start, int end) {
        return replaceChar(value, start, end, '*');
    }

    /**
     * 字符串替换, 以sep字符代替字符位置
     *
     * <pre>
     * StringUt.replaceChar("1234567890", 3, 8, '*') = "123*****90"
     * </pre>
     */
    public static String replaceChar(String value, int start, int end, char sep) {
        if (StringUtils.isBlank(value) && start >= end)
            return value;
        char[] chars = value.toCharArray();
        if (start < 0)
            start = 0;
        if (end < 0)
            end = chars.length;
        for (int i = 0; i < chars.length; i++) {
            if (start <= i && i < end) {
                chars[i] = sep;
            }
        }
        return new String(chars);
    }

    /**
     * 字符串替换
     *
     * <pre>
     * StringUt.replaceStr("1234567890", 3, 8, "^*") = "123^*90"
     * </pre>
     */
    public static String replaceStr(String value, int start, int end, String sep) {
        if (StringUtils.isBlank(value) && start >= end)
            return value;
        if (start < 0)
            start = 0;
        if (end < 0)
            end = value.length();
        return new StringBuilder(value).replace(start, end, sep).toString();
    }

}

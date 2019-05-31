package com.d2c.util.string;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinYinUtil {

    private static final Log logger = LogFactory.getLog(PinYinUtil.class);
    /**
     * 汉语拼音格式化工具类
     */
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    /**
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母
     *
     * @param src
     * @return
     */
    public static String getFullSpell(String src) {
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// u:的声母
        if (isNull(src)) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < src.length(); i++) {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if (ch >= 'a' && ch <= 'z')
                    sb.append((char) (ch - 'a' + 'A'));
                if (ch >= 'A' && ch <= 'Z')
                    sb.append(ch);
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if (arr == null || arr.length == 0) {
                    continue;
                }
                String s = arr[0];// 不管多音字,只取第一个
                char c = s.charAt(0);// 大写第一个字母
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                sb.append(pinyin).append("");
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 提取每个汉字的首字母(大写)
     *
     * @param str
     * @return
     */
    public static String getFirstSpell(String str) {
        if (isNull(str)) {
            return "";
        }
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        convert = string2AllTrim(convert);
        return convert.toUpperCase();
    }

    /**
     * 判断字符串是否为空
     *
     * @param strData
     * @return
     */
    private static boolean isNull(Object strData) {
        if (strData == null || String.valueOf(strData).trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 去掉字符串包含的所有空格
     *
     * @param value
     * @return
     */
    private static String string2AllTrim(String value) {
        if (isNull(value)) {
            return "";
        }
        return value.trim().replace(" ", "");
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean containChinese(String str) {
        if (isNull(str)) {
            return false;
        }
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否乱码
     *
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                    System.out.print(c);
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

}

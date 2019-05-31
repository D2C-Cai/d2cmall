package com.d2c.util.string;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }

    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * map解析为字符串
     *
     * @param params
     * @return
     */
    public static String mapToString(Map<?, ?> params) {
        String str = " ";
        try {
            if (params != null && params.size() > 0) {
                for (Object key : params.keySet()) {
                    Object values = params.get(key);
                    str = str + key + "=";
                    if (values instanceof String[]) {
                        String[] strs = (String[]) values;
                        for (String value : strs) {
                            str = str + value + ",";
                        }
                        str = str.substring(0, str.length() - 1) + ";";
                    }
                }
                str = str.substring(0, str.length() - 1);
            } else {
                str = "空";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return params.toString();
        }
        return str;
    }

    /**
     * xml字符串解析为map
     *
     * @param xmltext
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> xmlToMap(String xmltext) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(xmltext);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        Element root = document.getDocumentElement();
        HashMap<String, String> map = new HashMap<>();
        if (root != null) {
            Node node = root.getFirstChild();
            while (node != null) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    map.put(node.getNodeName(), node.getTextContent());
                }
                node = node.getNextSibling();
            }
        }
        return map;
    }

    public static String[] strToArray(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strs = str.split(",");
        return (String[]) ConvertUtils.convert(strs, String.class);
    }

    public static Long[] strToLongArray(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strs = str.split(",");
        return (Long[]) ConvertUtils.convert(strs, Long.class);
    }

    public static List<Long> strToLongList(String str) {
        List<Long> list = new ArrayList<>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        String[] strs = str.split(",");
        for (String s : strs) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            try {
                list.add(Long.parseLong(s));
            } catch (Exception e) {
                continue;
            }
        }
        return list;
    }

    public static String longArrayToStr(Long[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Long l : array) {
            if (l == null) {
                continue;
            }
            sb.append(l).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String longListToStr(List<Long> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Long l : list) {
            if (l == null) {
                continue;
            }
            sb.append(l).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 根据指定长度 分隔字符串
     *
     * @param str    需要处理的字符串
     * @param length 分隔长度
     * @return 字符串集合
     */
    public static List<String> splitString(String str, int length) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i += length) {
            int endIndex = i + length;
            if (endIndex <= str.length()) {
                list.add(str.substring(i, i + length));
            } else {
                list.add(str.substring(i, str.length() - 1));
            }
        }
        return list;
    }

    /**
     * 将字符串List转化为字符串，以分隔符间隔.
     *
     * @param list      需要处理的List.
     * @param separator 分隔符.
     * @return 转化后的字符串
     */
    public static String toString(List<String> list, String separator) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            stringBuffer.append(separator + str);
        }
        stringBuffer.deleteCharAt(0);
        return stringBuffer.toString();
    }

    /**
     * 替换href为parseUrl
     */
    public static String replaceHref(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replace("href=\"", "ng-click=\"parseUrl(\'");
            String regex = "ng-click=\"parseUrl\\(\'[^\"]+\"";
            Pattern pat = Pattern.compile(regex);
            Matcher matcher = pat.matcher(str);
            int end = 0;
            int i = 0;
            while (matcher.find()) {
                end = matcher.end();
                end = end + 2 * i++;
                str = str.substring(0, end - 1) + "\')\"" + str.substring(end, str.length());
            }
        }
        return str;
    }

    /**
     * 去html标签
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        if (htmlStr == null) {
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        htmlStr = htmlStr.replaceAll("&reg;", "");
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
        return htmlStr.trim(); // 返回文本字符串
    }

    public static List<String> getHTMLImg(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static Boolean hasBlack(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return false;
        }
        for (Object obj : objs) {
            if (isBlack(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlack(Object obj) {
        if (obj instanceof String) {
            return StringUtils.isBlank(String.valueOf(obj));
        } else {
            return obj == null;
        }
    }

    public static boolean checkMobile(String obj) {
        return match("1[0-9]{10}", obj);
    }

    /**
     * 是否是数字，包含小数点数字
     *
     * @param value
     * @return
     */
    public static boolean isNumber(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        String[] nums = value.split("\\.");
        if (nums.length > 2) {
            return false;
        }
        if (nums.length == 1) {
            return StringUtils.isNumeric(value);
        }
        return StringUtils.isNumeric(nums[0]) && StringUtils.isNumeric(nums[1]);
    }

}

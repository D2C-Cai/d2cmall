package com.d2c.order.third.payment.gongmall.sign;

import com.d2c.common.base.utils.security.MD5Util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SignHelper {

    public static final String APPKEY = "appKey";
    public static final String APPSECRET = "appSecret";
    public static final String NONCE = "nonce";
    public static final String TIMESTAMP = "timestamp";
    public static final String SIGN = "sign";

    /**
     * 获取签名
     *
     * @param paramMap  包含业务参数，和appKey,nonce,timestamp这3个公共参数
     * @param appSecret
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String appSecret) {
        String text = getUrlText(paramMap);
        text += "&appSecret=" + appSecret;
        return MD5Util.encodeMD5Hex(text).toUpperCase();
    }

    private static String getUrlText(Map<String, String> beanMap) {
        beanMap = getSortedMap(beanMap);
        StringBuilder builder = new StringBuilder();
        for (String key : beanMap.keySet()) {
            String value = beanMap.get(key).toString();
            builder.append(key);
            builder.append('=');
            builder.append(value);
            builder.append('&');
        }
        String text = builder.toString();
        return text.substring(0, text.length() - 1);
    }

    /**
     * 对普通map进行排序
     *
     * @param paramMap
     * @return
     */
    private static Map<String, String> getSortedMap(Map<String, String> paramMap) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        for (String key : paramMap.keySet()) {
            if (key != null && !APPSECRET.equals(key)) {
                String value = paramMap.get(key);
                if (value != null) {
                    String valueStr = String.valueOf(value);
                    if (valueStr != null && !"".equals(valueStr)) {
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    }

}
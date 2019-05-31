package com.d2c.common.base.utils;

import com.d2c.common.base.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 *
 * @author wull
 */
public class HttpUt {
    //******************** HTTP URL ***********************

    /**
     * URL添加参数
     */
    public static String getUrl(String url, Map<String, Object> params) {
        if (StringUt.isBlank(url)) return url;
        String pm = getUrlParams(params);
        if (StringUt.isBlank(pm)) return url;
        if (!StringUtils.contains(url, "?")) {
            url = url + "?";
        }
        return url + pm;
    }

    public static String getUrlParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return "";
        List<String> list = new ArrayList<>();
        params.forEach((k, v) -> list.add(k + "=" + v));
        return StringUt.join(list, "&");
    }
    //********************* HttpServletRequest **********************

    /**
     * 读取Body数据
     */
    public static String getBody(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return URLDecoder.decode(sb.toString(), "UTF-8");
        } catch (Exception e) {
            throw new BaseException("读取body数据失败...", e);
        }
    }

    public static String getAttr(HttpServletRequest request, String key) {
        Object obj = request.getAttribute(key);
        return obj != null ? (String) obj : null;
    }

    public static String getSessionAttr(HttpServletRequest request, String key) {
        return getSessionAttr(request.getSession(), key);
    }

    public static String getSessionAttr(HttpSession hs, String key) {
        Object obj = hs.getAttribute(key);
        return obj != null ? (String) obj : null;
    }

    /**
     * 是否Ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null ? "XMLHttpRequest".equals(header) : false;
    }

    /**
     * 获取登入IP
     */
    public static String getLoginIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}

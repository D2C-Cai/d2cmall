package com.d2c.common.base.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie操作工具
 *
 * @author wull
 */
public class CookieUt {

    /**
     * 按名称获取cookie
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isBlank(name)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 添加cookie数据
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        // Cookie安全方法，servlet-api-3.0后版本支持
        // cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 清除cookie
     */
    public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {
        Cookie cookie = new Cookie(name, null);
        if (path != null) {
            cookie.setPath(path);
        }
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(-1000);
        response.addCookie(cookie);
    }

}

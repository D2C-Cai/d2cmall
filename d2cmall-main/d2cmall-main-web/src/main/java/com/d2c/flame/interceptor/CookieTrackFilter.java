package com.d2c.flame.interceptor;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过广告链接进官网
 *
 * @author Administrator
 */
public class CookieTrackFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getHeader("USER-AGENT").contains("weibo")) {
            // 从微博下单的标记
            Cookie cookie = new Cookie("d2cCookie", this.build("Weibo", "0"));
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        if (request.getParameter("parent_id") != null) {
            // 从分销下单的标记
            Cookie cookie = new Cookie("d2cCookie", this.build("Partner", request.getParameter("parent_id")));
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        filterChain.doFilter(req, resp);
    }

    private String build(Object channel, Object info) {
        return "channel:" + channel + ",info:" + info;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}

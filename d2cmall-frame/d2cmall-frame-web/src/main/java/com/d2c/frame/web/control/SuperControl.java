package com.d2c.frame.web.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 父类基础控制层类
 */
public class SuperControl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取Session会话
     */
    protected HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
            logger.error("获取Session失败...", e);
        }
        return session;
    }

    /**
     * 获取Request
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取Response
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取Request.getParameter参数
     */
    protected String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取header.User-Agent
     */
    protected String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }

}

package com.d2c.frame.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * WEB 工具类
 *
 * @author wull
 */
public class WebUt {

    private static final Logger logger = LoggerFactory.getLogger(WebUt.class);

    /**
     * 获取拦截器方法
     */
    public static Method getMethodOnHandler(Object handler) {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethod();
        } catch (Exception e) {
            logger.error("获取拦截器方法失败...handler:" + handler, e);
            return null;
        }
    }

}

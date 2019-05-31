package com.d2c.frame.web.control;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * web基础控制层类
 */
public class BaseControl extends SuperControl {

    /**
     * 获取登录IP
     */
    protected String getLoginIp() {
        HttpServletRequest request = getRequest();
        String userIp = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr()
                : request.getHeader("x-forwarded-for");
        return userIp.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : userIp;
    }

    /**
     * 设置cache的key值
     */
    protected String cacheKey(Object... keys) {
        String key = "";
        for (Object o : keys) {
            if (o == null)
                continue;
            if (StringUtils.isBlank(key)) {
                key = o.toString();
            } else {
                key = key + ":" + o.toString();
            }
        }
        return key;
    }

    /**
     * 解决当日期为空时,转换错误的问题
     *
     * @param dataBinder
     */
    @org.springframework.web.bind.annotation.InitBinder
    protected void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
            }

            @Override
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                    }
                } catch (ParseException e) {
                    setValue(null);
                }
            }
        });
    }

}

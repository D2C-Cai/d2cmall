package com.d2c.flame.freemarker;

import com.d2c.flame.property.HttpProperties;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RenderUserAgent implements TemplateMethodModelEx {

    protected static final Log logger = LogFactory.getLog(RenderUserAgent.class);

    /**
     * 获取 request
     *
     * @return
     */
    protected static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 获取cookie
     *
     * @return
     */
    protected static boolean getUserAgentCookie() {
        String uac = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && cookies.length >= 1) {
            for (int j = 0; j < cookies.length; j++) {
                if (cookies[j].getName().equalsIgnoreCase(HttpProperties.USERAGENT)) {
                    uac = cookies[j].getValue();
                    break;
                }
            }
        }
        return (uac != null && uac.equals("1")) ? true : false;
    }

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
        String userAgent = getRequest().getHeader("USER-AGENT");
        if (StringUtils.isNotBlank(userAgent) && userAgent.contains("d2cmall")) {
            return true;
        } else if (getUserAgentCookie()) {
            return true;
        } else {
            return false;
        }
    }

}

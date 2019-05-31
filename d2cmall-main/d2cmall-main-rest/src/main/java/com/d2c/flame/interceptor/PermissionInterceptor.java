package com.d2c.flame.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.core.propery.AppProperties;
import com.d2c.frame.web.annotation.SignIgnore;
import com.d2c.frame.web.annotation.SignMethod;
import com.d2c.frame.web.utils.WebUt;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // if (appProperties.getDebug()) {
        // return true;
        // }
        // 根据标签判断是否签名验证
        Boolean isSign = checkSignAnn(handler);
        if (isSign == null) {
            isSign = StringUtil.isNotBlank(getToken(request));
        }
        if (!isSign) {
            return true;
        }
        // 签名验证
        Map<String, ?> params = request.getParameterMap();
        String sign = getSign(params);
        String queryStr = createLinkString(filterParams(params));
        String signStr = MD5Util.encodeMD5Hex(queryStr + getToken(request));
        if (sign.equals(signStr)) {
            return true;
        }
        writeError(response);
        return false;
    }

    /**
     * 根据标签判断是否签名验证
     */
    private Boolean checkSignAnn(Object handler) {
        Method method = WebUt.getMethodOnHandler(handler);
        if (method != null) {
            if (method.isAnnotationPresent(SignMethod.class)) {
                return true;
            } else if (method.isAnnotationPresent(SignIgnore.class)) {
                return false;
            }
        }
        return null;
    }

    private String getSign(Map<String, ?> params) {
        String sign = "";
        if (params.get("sign") != null) {
            Object value = params.get("sign");
            if (value instanceof String) {
                sign = value.toString();
            } else if (value instanceof String[]) {
                sign = ((String[]) value)[0];
            }
        }
        return sign;
    }

    private Map<String, String> filterParams(Map<String, ?> params) throws Exception {
        Map<String, String> result = new TreeMap<String, String>();
        if (params == null || params.size() <= 0) {
            return result;
        }
        String value = "";
        for (String key : params.keySet()) {
            if (params.get(key) instanceof String) {
                value = (String) params.get(key);
            } else if (params.get(key) instanceof String[]) {
                value = ((String[]) params.get(key))[0];
            }
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20"));
        }
        return result;
    }

    private String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("accesstoken");
        return token == null ? "" : token;
    }

    private void writeError(HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        result.setStatus(-1);
        result.setMsg("Invalid signature!");
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSON(result));
        } finally {
            writer.close();
        }
    }

}

package com.d2c.flame.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.Config;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("accesstoken");
        if (token != null) {
            String day = token.split("-")[0];
            if (token.equals(Config.getToken(day))) {
                return true;
            }
        }
        ResponseResult result = new ResponseResult();
        result.setStatus(-1);
        result.setLogin(false);
        result.setMsg("No Permission!");
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSON(result));
        } finally {
            writer.close();
        }
        return false;
    }

}

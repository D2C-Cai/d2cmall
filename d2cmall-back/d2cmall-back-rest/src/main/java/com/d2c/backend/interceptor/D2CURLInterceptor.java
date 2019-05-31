package com.d2c.backend.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ErrorCode;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.core.propery.AppProperties;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Resource;
import com.d2c.member.model.Role;
import com.d2c.member.service.AdminService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.ResourceService;
import com.d2c.member.service.RoleService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class D2CURLInterceptor extends HandlerInterceptorAdapter {

    protected static final Log logger = LogFactory.getLog(D2CURLInterceptor.class);
    private static final String ROLE_DESIGNER = "ROLE_DESIGNER";
    private static final String ROLE_STORE = "ROLE_STORE";
    @Autowired
    protected MemberInfoService memberInfoService;
    @Autowired
    protected AppProperties properties;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (properties.getDebug()) {
            return super.preHandle(request, response, handler);
        }
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtil.isNotBlank(queryString)) {
            url += "?" + queryString;
        }
        Resource resource = resourceService.findByValue(url);
        if (resource == null) {
            // 未注册地址不受权限控制
            return super.preHandle(request, response, handler);
        }
        // 地址允许访问的角色
        List<Role> roles = roleService.findByResourceId(resource.getId());
        if (roles == null || roles.size() == 0) {
            this.writerError(response, "您没有权限！", ErrorCode.NO_PERMISSION);
            return false;
        }
        // 目前登录账户的角色
        List<String> roleValues = this.getRoleValues(request);
        if (roleValues == null || roleValues.size() == 0) {
            this.writerError(response, "请重新登入！", ErrorCode.NO_LOGIN);
            return false;
        }
        // 若账户角色存在于地址允许角色之内，则通过校验
        for (Role r : roles) {
            if (roleValues.contains(r.getValue())) {
                return super.preHandle(request, response, handler);
            }
        }
        this.writerError(response, "您没有权限！", ErrorCode.NO_PERMISSION);
        return false;
    }

    private List<String> getRoleValues(HttpServletRequest request) {
        String tgt = request.getHeader("accesstoken");
        AdminDto admin = adminService.findAdminByTicket(tgt);
        if (admin != null) {
            return admin.getRoleValues();
        } else {
            List<String> roleValues = new ArrayList<>();
            MemberInfo member = memberInfoService.findByToken(tgt);
            if (member == null) {
            } else if (member.getDesignerId() != null) {
                roleValues.add(ROLE_DESIGNER);
            } else if (member.getStoreId() != null) {
                roleValues.add(ROLE_STORE);
            }
            return roleValues;
        }
    }

    private void writerError(HttpServletResponse response, String msg, ErrorCode errorCode) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Response error = new ErrorResponse(errorCode.getCode(), msg);
        error.setLogin(false);
        String json = JSONObject.toJSONString(error);
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

}

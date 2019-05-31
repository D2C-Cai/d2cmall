package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class AdminSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 姓名
     */
    private String name;
    /**
     * 登录名
     */
    private String username;
    /**
     * 部门
     */
    private String department;
    /**
     * 手机号码
     */
    private String mobile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}

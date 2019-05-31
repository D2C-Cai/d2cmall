package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Table;
import java.util.Date;

/**
 * 后台账号
 */
@Table(name = "m_admin")
public class Admin extends PreUserDO {

    /**
     * 保存登录会员ID的Session名称
     */
    public static final String LOGIN_ADMIN_ID_SESSION_NAME = "loginAdmin";
    public static final String CASTGC = "ADMIN_CASTGC";
    private static final long serialVersionUID = 1L;
    /**
     * 部门
     */
    private String department;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 名称
     */
    private String name;
    /**
     * 登录账号
     */
    @AssertColumn("用户账号不能为空")
    private String username;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 账号是否启用
     */
    private Boolean isAccountEnabled = true;
    /**
     * 账号是否锁定
     */
    private Boolean isAccountLocked = false;
    /**
     * 账号是否过期
     */
    private Boolean isAccountExpired = false;
    /**
     * 凭证是否过期
     */
    private Boolean isCredentialsExpired = true;
    /**
     * 锁定日期
     */
    private Date lockedDate;
    /**
     * 登录日期
     */
    private Date loginDate;
    /**
     * 登录不成功次数
     */
    private Integer loginFailureCount = 0;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 用户令牌 Ticket Grangting Ticket
     */
    private String tgt;

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAccountEnabled() {
        return isAccountEnabled;
    }

    public void setIsAccountEnabled(Boolean isAccountEnabled) {
        this.isAccountEnabled = isAccountEnabled;
    }

    public Boolean getIsAccountLocked() {
        return isAccountLocked;
    }

    public void setIsAccountLocked(Boolean isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    public Boolean getIsAccountExpired() {
        return isAccountExpired;
    }

    public void setIsAccountExpired(Boolean isAccountExpired) {
        this.isAccountExpired = isAccountExpired;
    }

    public Boolean getIsCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
        this.isCredentialsExpired = isCredentialsExpired;
    }

    public String getTgt() {
        return tgt;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}

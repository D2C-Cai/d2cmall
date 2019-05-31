package com.d2c.behavior.entity;

import com.d2c.common.api.model.FullDO;

/**
 * 账户表
 *
 * @author wull
 */
public class UserDO extends FullDO {

    private static final long serialVersionUID = 8892073391665511551L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * APP系统ID
     */
    private Integer appId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

}

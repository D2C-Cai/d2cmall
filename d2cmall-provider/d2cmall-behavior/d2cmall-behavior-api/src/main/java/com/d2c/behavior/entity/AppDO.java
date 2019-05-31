package com.d2c.behavior.entity;

import com.d2c.common.api.model.FullDO;

/**
 * APP系统表
 *
 * @author wull
 */
public class AppDO extends FullDO {

    private static final long serialVersionUID = 8892073391665511551L;
    /**
     * APP名称
     */
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}

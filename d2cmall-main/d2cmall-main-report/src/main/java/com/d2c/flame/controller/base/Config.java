package com.d2c.flame.controller.base;

import com.d2c.common.base.utils.security.MD5Util;

public class Config {

    public final static String PASSWORD = "report";
    public final static String SALT = "d2cmall-main-report";

    public static String getToken(String day) {
        return day + "-" + MD5Util.encodeMD5Hex(Config.SALT + day);
    }

}

package com.d2c.behavior.mongo.helper;

import com.d2c.common.base.utils.StringUt;

public class VisitorFactory {

    /**
     * 访客昵称
     */
    public static String getNickname() {
        return "匿名" + StringUt.nextInt(1000);
    }

}

package com.d2c.common.mongodb.utils;

import org.bson.types.ObjectId;

/**
 * Mongo 工具类
 *
 * @author wull
 */
public class MongoUt {

    /**
     * 对象不能为空
     */
    public static String newId() {
        return new ObjectId().toString();
    }

}

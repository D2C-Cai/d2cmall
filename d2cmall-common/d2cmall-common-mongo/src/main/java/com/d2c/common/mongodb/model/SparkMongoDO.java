package com.d2c.common.mongodb.model;

/**
 * Spark 基本类
 *
 * @author wull
 */
public abstract class SparkMongoDO extends BaseMongoDO {

    private static final long serialVersionUID = 6444408110484415791L;
    protected Boolean done = false;

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

}

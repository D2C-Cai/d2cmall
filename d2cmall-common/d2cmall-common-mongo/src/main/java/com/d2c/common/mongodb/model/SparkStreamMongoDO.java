package com.d2c.common.mongodb.model;

/**
 * Spark 基本类
 *
 * @author wull
 */
public abstract class SparkStreamMongoDO extends BaseMongoDO {

    private static final long serialVersionUID = 6444408110484415791L;
    protected Boolean streamUpdate = false;

    public Boolean getStreamUpdate() {
        return streamUpdate;
    }

    public void setStreamUpdate(Boolean needUpdate) {
        this.streamUpdate = needUpdate;
    }

}

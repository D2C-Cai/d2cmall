package com.d2c.common.mongodb.model;

import java.util.Date;

/**
 * 实体类 - 超类
 */
public abstract class SuperMongoDO extends EmtryMongoDO {

    private static final long serialVersionUID = 1L;
    /**
     * 修改时间
     */
    protected Date gmtModified;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}

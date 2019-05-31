package com.d2c.common.mongodb.model;

import org.springframework.data.annotation.Id;

/**
 * 实体类 - 基类
 * MongoDB 内置 ObjectId
 *
 * @author wull
 */
public abstract class BaseMongoDO extends SuperMongoDO {

    private static final long serialVersionUID = 1L;
    @Id
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

package com.d2c.common.api.model;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 实体类 - 基类
 */
@Entity
public abstract class BaseDO extends BaseSuperDO {

    private static final long serialVersionUID = 1L;
    /**
     * 创建日期
     */
    protected Date gmtCreate;
    /**
     * 修改日期
     */
    protected Date gmtModified;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}

package com.d2c.common.api.dto;

import java.util.Date;

public abstract class EntityTimeDTO extends EntityDTO {

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

package com.d2c.common.api.dto;

import java.util.Date;

public abstract class MongoDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    protected Date gmtModified;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}

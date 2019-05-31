package com.d2c.common.api.model;

import javax.persistence.Entity;

/**
 * 实体类 - 基类
 */
@Entity
public abstract class FullDO extends BaseUserDO implements DeleteAble {

    private static final long serialVersionUID = 1L;
    protected Boolean isDeleted = false;

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}

package com.d2c.common.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - 基类
 */
@Entity
public abstract class BaseUserDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人
     */
    @Column(insertable = true, updatable = false)
    protected String creator;
    /**
     * 修改人
     */
    @Column(insertable = true, updatable = false)
    protected String modifier;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

}

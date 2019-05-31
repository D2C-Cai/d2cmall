package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 后台角色
 */
@Table(name = "m_role")
public class Role extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("角色名称不能为空")
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 是否内置
     */
    private Integer isSystem = 0;
    /**
     * 描述
     */
    private String description;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
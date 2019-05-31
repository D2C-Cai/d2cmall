package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.member.model.DiscountSettingGroup.GroupType;

public class DiscountSettingGroupSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private GroupType type;
    /**
     * 名称
     */
    private String name;
    /**
     * 0停用，1启用
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

}

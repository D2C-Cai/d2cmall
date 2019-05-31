package com.d2c.product.dto;

import com.d2c.product.model.SalesProperty;

public class SalesPropertyDto extends SalesProperty {

    private static final long serialVersionUID = 1L;
    /**
     * 规格组名称
     */
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}

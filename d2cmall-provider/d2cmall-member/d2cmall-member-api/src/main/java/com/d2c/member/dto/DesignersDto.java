package com.d2c.member.dto;

import com.d2c.member.model.Designers;

public class DesignersDto extends Designers {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌列表
     */
    private Object[] brandList;

    public Object[] getBrandList() {
        return brandList;
    }

    public void setBrandList(Object[] brandList) {
        this.brandList = brandList;
    }

}

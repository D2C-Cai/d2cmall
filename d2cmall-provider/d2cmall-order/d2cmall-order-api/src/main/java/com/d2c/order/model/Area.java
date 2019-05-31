package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.BaseParentDO;

import javax.persistence.Table;

/**
 * 地理区域
 */
@Table(name = "o_area")
public class Area extends BaseParentDO<Integer> {

    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    @AssertColumn("编码不能为空")
    private Integer code;
    /**
     * 名称
     */
    @AssertColumn("名称不能为空")
    private String name;

    public Area() {
    }

    public Area(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return code;
    }

    @Override
    public void setId(Integer id) {
        this.code = id;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.getCode());
        obj.put("name", this.getName());
        return obj;
    }

}

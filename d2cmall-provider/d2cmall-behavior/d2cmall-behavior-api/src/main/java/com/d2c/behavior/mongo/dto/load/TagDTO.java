package com.d2c.behavior.mongo.dto.load;

import com.d2c.common.api.dto.BaseDTO;

/**
 * 标签导入数据
 *
 * @author wull
 */
public class TagDTO extends BaseDTO {

    private static final long serialVersionUID = -4353127288827880538L;
    /**
     * 名称
     */
    private String name;
    /**
     * 数值
     */
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}

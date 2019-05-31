package com.d2c.common.api.dto;

public abstract class EntityDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

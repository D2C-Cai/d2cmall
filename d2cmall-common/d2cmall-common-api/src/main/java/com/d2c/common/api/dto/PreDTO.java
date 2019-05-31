package com.d2c.common.api.dto;

public abstract class PreDTO extends BaseParentDTO<Long> {

    private static final long serialVersionUID = 1L;
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

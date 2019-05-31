package com.d2c.common.api.dto;

public abstract class EntityUserDTO extends EntityDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人
     */
    protected String creator;
    /**
     * 创建人
     */
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

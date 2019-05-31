package com.d2c.common.api.dto;

import java.util.Date;

public abstract class PreUserDTO extends PreDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人
     */
    protected String creator;
    /**
     * 最后修改人
     */
    protected String lastModifyMan;
    /**
     * 创建日期
     */
    protected Date createDate;
    /**
     * 修改日期
     */
    protected Date modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifyMan() {
        return lastModifyMan;
    }

    public void setLastModifyMan(String lastModifyMan) {
        this.lastModifyMan = lastModifyMan;
    }

}

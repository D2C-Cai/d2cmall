package com.d2c.common.search.model;

import java.util.Date;

public class PreSearchDO extends ParentSearchDO<Long> {

    private static final long serialVersionUID = -5065638299802785711L;
    /**
     * 创建日期
     */
    protected Date createDate;
    /**
     * 修改日期
     */
    protected Date modifyDate;

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

}

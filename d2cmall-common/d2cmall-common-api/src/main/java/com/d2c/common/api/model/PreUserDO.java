package com.d2c.common.api.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 之前数据库使用的实体类
 */
@Entity
public abstract class PreUserDO extends BaseParentDO<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    protected Long id;
    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = true, updatable = false)
    protected Date createDate = new Date();
    /**
     * 创建人
     */
    @Column(insertable = true, updatable = false)
    protected String creator;
    /**
     * 修改日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifyDate = new Date();
    /**
     * 最后修改人
     */
    protected String lastModifyMan;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getLastModifyMan() {
        return lastModifyMan;
    }

    public void setLastModifyMan(String lastModifyMan) {
        this.lastModifyMan = lastModifyMan;
    }

}

package com.d2c.common.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 实体类 - 基类
 */
@Entity
public abstract class BaseSuperDO extends BaseParentDO<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

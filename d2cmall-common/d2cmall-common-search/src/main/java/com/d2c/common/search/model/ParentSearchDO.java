package com.d2c.common.search.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class ParentSearchDO<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}

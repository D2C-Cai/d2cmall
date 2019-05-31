package com.d2c.common.test;

import javax.persistence.Entity;

@Entity
public class BaseTestDTO {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

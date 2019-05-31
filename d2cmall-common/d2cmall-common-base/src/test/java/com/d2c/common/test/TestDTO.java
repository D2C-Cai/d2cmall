package com.d2c.common.test;

import java.io.Serializable;

public class TestDTO implements Serializable {

    private static final long serialVersionUID = -2000593000626928987L;
    private String name;
    private Integer age;
    private UserDTO user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}

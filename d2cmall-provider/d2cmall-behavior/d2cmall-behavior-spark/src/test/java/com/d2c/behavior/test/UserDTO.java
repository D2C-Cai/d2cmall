package com.d2c.behavior.test;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -5911604311298918996L;
    private String name;
    private UserDTO user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}

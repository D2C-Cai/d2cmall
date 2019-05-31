package com.d2c.common.api.security;

import java.io.Serializable;

public interface CurrentUser extends Serializable {

    public Object getId();

    public String getUsername();

}

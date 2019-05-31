package com.d2c.common.core.test.view;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserVO implements Serializable {

    private static final long serialVersionUID = -8342933798927608809L;
    private String userName;
    private BigDecimal price;
    private Integer count;
    private String loginCode;
    private String date;

    public UserVO() {
    }

    public UserVO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

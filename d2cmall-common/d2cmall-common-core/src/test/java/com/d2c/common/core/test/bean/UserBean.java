package com.d2c.common.core.test.bean;

import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.test.view.UserVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserBean implements Serializable {

    private static final long serialVersionUID = -8342933798927608809L;
    private String userName;
    private BigDecimal price;
    private Integer count;
    private String loginCode;
    private Date date;

    public UserBean() {
    }

    public UserBean(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return JsonUt.serialize(this);
    }

    public UserVO toView() {
        return ConvertHelper.convert(this, UserVO.class);
    }
    //**********************************

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

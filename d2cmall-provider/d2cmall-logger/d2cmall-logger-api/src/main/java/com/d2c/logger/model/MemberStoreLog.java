package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "log_member_store")
public class MemberStoreLog extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 注册时间
     */
    private Date registerDate;
    /**
     * 城市
     */
    private String city;
    /**
     * 门店名称
     */
    private String storeName;

    public MemberStoreLog() {
    }

    public MemberStoreLog(String loginCode, Date registerDate, String city, String storeName) {
        this.loginCode = loginCode;
        this.registerDate = registerDate;
        this.city = city;
        this.storeName = storeName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}

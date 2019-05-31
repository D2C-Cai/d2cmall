package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 物流公司
 */
@Table(name = "o_logistics_company")
public class LogisticsCompany extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 快递公司名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 电话
     */
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}

package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class StoreSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    public String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 城市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 经营方式 (DIRECT:直营，DPLUS:D+店)
     */
    private String busType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

}

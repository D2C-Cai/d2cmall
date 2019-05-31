package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户快递地址
 *
 * @author wull
 */
@Document
public class PersonAddressDO extends BaseMongoDO {

    private static final long serialVersionUID = 5049158952120571446L;
    @Indexed
    private String personId;
    /**
     * 用户名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 默认
     */
    private boolean isDefault;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区号
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

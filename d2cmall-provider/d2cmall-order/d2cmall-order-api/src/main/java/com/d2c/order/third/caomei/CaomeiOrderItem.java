package com.d2c.order.third.caomei;

import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;

import java.util.List;

public class CaomeiOrderItem {

    /**
     * 姓名
     */
    private String firstname;
    private String lastname;
    /**
     * 详细地址
     */
    private String addr1;
    /**
     * 州
     */
    private String state;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 手机
     */
    private String mob;
    /**
     * 商品
     */
    private String products;
    /**
     * 语言ID
     */
    private String langId = "80";
    /**
     * 身份证
     */
    private String idno;

    public String toParam(Order order, List<OrderItem> list, String idno) {
        this.firstname = order.getBuyerInfo();
        this.addr1 = order.getAddress();
        this.city = order.getCity();
        this.state = order.getProvince();
        this.country = "";
        this.mob = order.getMemberMobile();
        this.products = "";
        list.forEach(oi -> this.products = this.products + oi.getProductSn() + ",");
        this.idno = idno;
        if (firstname == null) {
            return null;
        }
        return null;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

}

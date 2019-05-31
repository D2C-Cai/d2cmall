package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

public class OrderConfirmBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货人
     */
    private String reciver;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 卖家备注
     */
    private String adminMemo;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

}

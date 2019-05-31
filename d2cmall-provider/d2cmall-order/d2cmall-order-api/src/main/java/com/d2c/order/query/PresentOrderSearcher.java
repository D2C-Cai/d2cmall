package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PresentOrderSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 买家账号
     */
    private String loginCode;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 收礼物账号
     */
    private String receiveLoginCode;
    /**
     * 收礼物id
     */
    private Long receiveMemberId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 礼物名称
     */
    private String productName;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品id
     */
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getReceiveLoginCode() {
        return receiveLoginCode;
    }

    public void setReceiveLoginCode(String receiveLoginCode) {
        this.receiveLoginCode = receiveLoginCode;
    }

    public Long getReceiveMemberId() {
        return receiveMemberId;
    }

    public void setReceiveMemberId(Long receiveMemberId) {
        this.receiveMemberId = receiveMemberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

}

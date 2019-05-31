package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class ComplaintSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 联系人
     */
    private String name;
    /**
     * 投诉内容
     */
    private String content;
    /**
     * 投诉类别
     */
    private String type;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 投诉单状态
     */
    private String status;
    /**
     * 登记人
     */
    private String creator;
    /**
     * 登记时间
     */
    private Date beginCreateDate;
    private Date endCreateDate;
    /**
     * 反馈时间
     */
    private Date beginCmpDate;
    private Date endCmpDate;
    /**
     * 承诺时间
     */
    private Date beginPromiseDate;
    private Date endPromiseDate;
    /**
     * 完结时间
     */
    private Date beginOverDate;
    private Date endOverDate;
    /**
     * 业务单号
     */
    private String transactionSn;
    /**
     * 紧急程度 1->3 高->低
     */
    private Integer level;
    /**
     * 业务类型
     */
    private String businessType;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Date getBeginPromiseDate() {
        return beginPromiseDate;
    }

    public void setBeginPromiseDate(Date beginPromiseDate) {
        this.beginPromiseDate = beginPromiseDate;
    }

    public Date getEndPromiseDate() {
        return endPromiseDate;
    }

    public void setEndPromiseDate(Date endPromiseDate) {
        this.endPromiseDate = endPromiseDate;
    }

    public Date getBeginOverDate() {
        return beginOverDate;
    }

    public void setBeginOverDate(Date beginOverDate) {
        this.beginOverDate = beginOverDate;
    }

    public Date getEndOverDate() {
        return endOverDate;
    }

    public void setEndOverDate(Date endOverDate) {
        this.endOverDate = endOverDate;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }

    public Date getBeginCmpDate() {
        return beginCmpDate;
    }

    public void setBeginCmpDate(Date beginCmpDate) {
        this.beginCmpDate = beginCmpDate;
    }

    public Date getEndCmpDate() {
        return endCmpDate;
    }

    public void setEndCmpDate(Date endCmpDate) {
        this.endCmpDate = endCmpDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

}

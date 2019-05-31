package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class O2oSubscribeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 预约单号
     */
    private String sn;
    /**
     * 会员Id
     */
    private Long memberId;
    /**
     * 门店Id
     */
    private Long storeId;
    /**
     * 1用户已提交预约，2客服已通知门店，3门店准备中，4门店准备完成，5服务已完成，6客户已评价
     */
    private Integer[] status;
    /**
     * 状态大于此值
     */
    private Integer gtStatus;
    /**
     * 付款状态
     */
    private Integer payStatus;
    /**
     * 预约单数量
     */
    private Integer totalQuantity;
    /**
     * 预约单商品数量
     */
    private Integer productQuantity;
    /**
     * 预约单商品金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 成交数量
     */
    private Integer dealQuantity;
    /**
     * 成交金额
     */
    private BigDecimal dealAmount = new BigDecimal(0);
    /**
     * 客户联系电话
     */
    private String tel;
    /**
     * 时间查询
     */
    private String timeStamp;
    /**
     * 自定义条件
     */
    private String cusStr;
    /**
     * 预定抵达日期
     */
    private Date startDate;
    private Date endDate;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        if (status != null && status.length > 0 && status[0] == 88) {
            this.setGtStatus(0);
        } else {
            this.status = status;
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getGtStatus() {
        return gtStatus;
    }

    public void setGtStatus(Integer gtStatus) {
        this.gtStatus = gtStatus;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCusStr() {
        return cusStr;
    }

    public void setCusStr(String cusStr) {
        this.cusStr = cusStr;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getDealQuantity() {
        return dealQuantity;
    }

    public void setDealQuantity(Integer dealQuantity) {
        this.dealQuantity = dealQuantity;
    }

}

package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PartnerBillSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 返利单编号
     */
    private String sn;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 直接返利ID
     */
    private Long partnerId;
    /**
     * 上级分销ID
     */
    private Long parentId;
    /**
     * 间接返利ID
     */
    private Long superId;
    /**
     * 顶级返利ID
     */
    private Long masterId;
    /**
     * 直接返利账号
     */
    private String partnerCode;
    /**
     * 间接返利账号
     */
    private String parentCode;
    /**
     * 直接返利级别
     */
    private Integer partnerLevel;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 创建时间开始
     */
    private Date startDate;
    /**
     * 创建时间结束
     */
    private Date endDate;
    /**
     * 是否异常
     */
    private Integer excep;
    /**
     * 大于状态
     */
    private Integer gtStatus;
    /**
     * 门店标识
     */
    private Integer storeMark;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getPartnerLevel() {
        return partnerLevel;
    }

    public void setPartnerLevel(Integer partnerLevel) {
        this.partnerLevel = partnerLevel;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public Integer getExcep() {
        return excep;
    }

    public void setExcep(Integer excep) {
        this.excep = excep;
    }

    public Integer getGtStatus() {
        return gtStatus;
    }

    public void setGtStatus(Integer gtStatus) {
        this.gtStatus = gtStatus;
    }

    public Integer getStoreMark() {
        return storeMark;
    }

    public void setStoreMark(Integer storeMark) {
        this.storeMark = storeMark;
    }

}

package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class BrandDetailSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌
     */
    private String brandName;
    /**
     * 设计师
     */
    private String designer;
    /**
     * 合同开始时间
     */
    private Date beginContractStartDate;
    private Date endContractStartDate;
    /**
     * 合同结束时间
     */
    private Date beginContractEndDate;
    private Date endContractEndDate;
    /**
     * 合同状态
     */
    private Integer contractStatus;
    /**
     * 招商人
     */
    private String merchantsMan;
    /**
     * 合同警告
     */
    private Integer warn;
    /**
     * 品牌ID
     */
    private String designerId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 运营小组
     */
    private String operation;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public Date getBeginContractStartDate() {
        return beginContractStartDate;
    }

    public void setBeginContractStartDate(Date beginContractStartDate) {
        this.beginContractStartDate = beginContractStartDate;
    }

    public Date getEndContractStartDate() {
        return endContractStartDate;
    }

    public void setEndContractStartDate(Date endContractStartDate) {
        this.endContractStartDate = endContractStartDate;
    }

    public Date getBeginContractEndDate() {
        return beginContractEndDate;
    }

    public void setBeginContractEndDate(Date beginContractEndDate) {
        this.beginContractEndDate = beginContractEndDate;
    }

    public Date getEndContractEndDate() {
        return endContractEndDate;
    }

    public void setEndContractEndDate(Date endContractEndDate) {
        this.endContractEndDate = endContractEndDate;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getMerchantsMan() {
        return merchantsMan;
    }

    public void setMerchantsMan(String merchantsMan) {
        this.merchantsMan = merchantsMan;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}

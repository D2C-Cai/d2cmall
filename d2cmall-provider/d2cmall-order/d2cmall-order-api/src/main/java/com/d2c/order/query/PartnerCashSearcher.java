package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PartnerCashSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 单号
     */
    private String sn;
    /**
     * 分销商账号
     */
    private String partnerCode;
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    /**
     * 分销商ID
     */
    private Long partnerId;
    /**
     * 运营审核人
     */
    private String confirmMan;
    /**
     * 待审核
     */
    private Integer waitConfirm;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getConfirmMan() {
        return confirmMan;
    }

    public void setConfirmMan(String confirmMan) {
        this.confirmMan = confirmMan;
    }

    public Integer getWaitConfirm() {
        return waitConfirm;
    }

    public void setWaitConfirm(Integer waitConfirm) {
        this.waitConfirm = waitConfirm;
    }

}

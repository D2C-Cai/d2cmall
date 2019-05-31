package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MagazineIssueSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 杂志定义ID
     */
    private Long magazineId;
    /**
     * 杂志定义名称
     */
    private String magazineName;
    /**
     * 间接返利ID
     */
    private Long partnerId;
    /**
     * 间接返利账号
     */
    private String partnerCode;
    /**
     * 直接返利ID
     */
    private Long partnerTraderId;
    /**
     * 直接返利账号
     */
    private String partnerTraderCode;
    /**
     * 状态 0未发行 1已发行
     */
    private Integer status;
    /**
     * 杂志编码
     */
    private String code;

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Long getPartnerTraderId() {
        return partnerTraderId;
    }

    public void setPartnerTraderId(Long partnerTraderId) {
        this.partnerTraderId = partnerTraderId;
    }

    public String getPartnerTraderCode() {
        return partnerTraderCode;
    }

    public void setPartnerTraderCode(String partnerTraderCode) {
        this.partnerTraderCode = partnerTraderCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

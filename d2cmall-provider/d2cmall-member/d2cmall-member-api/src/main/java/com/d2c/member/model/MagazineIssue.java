package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;

/**
 * 杂志发行本
 */
@Table(name = "m_magazine_issue")
public class MagazineIssue extends PreUserDO {

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
     * 编码
     */
    private String code;
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

    public MagazineIssue() {
    }

    public MagazineIssue(Magazine magazine) {
        super();
        this.status = 0;
        this.code = SerialNumUtil.buildMagazineIssueSn();
        this.magazineId = magazine.getId();
        this.magazineName = magazine.getName();
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("magazineId", this.getMagazineId());
        obj.put("magazineName", this.getMagazineName());
        obj.put("code", this.getCode());
        obj.put("partnerId", this.getPartnerId());
        obj.put("partnerTraderId", this.getPartnerTraderId());
        obj.put("isPartner", this.getPartnerTraderId() == null ? false : true);
        return obj;
    }

}

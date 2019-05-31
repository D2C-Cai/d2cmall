package com.d2c.product.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 品牌档案
 *
 * @author Lain
 */
@Table(name = "p_brand_detail")
public class BrandDetail extends PreUserDO {

    private static final long serialVersionUID = 1L;
    private static final String PARTNER_RATIO = "{\"POPPRODUCT\":{\"firstRatio\":0,\"secondRatio\":0,\"grossRatio\":1},\"BUYOUT\":{\"firstRatio\":0,\"secondRatio\":0,\"grossRatio\":1},\"CONSIGN\":{\"firstRatio\":0,\"secondRatio\":0,\"grossRatio\":1},\"SELF\":{\"firstRatio\":0,\"secondRatio\":0,\"grossRatio\":1},\"COOPERATIVE\":{\"firstRatio\":0,\"secondRatio\":0,\"grossRatio\":1}}";
    /**
     * 状态(0待完善，1已完善)
     */
    private Integer status;
    /**
     * 招商人
     */
    private String merchantsMan;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 公司
     */
    private String company;
    /**
     * 设计师
     */
    private String designer;
    /**
     * 合同状态
     */
    private Integer contractStatus;
    /**
     * 联系人
     */
    private String linker;
    /**
     * 联系方式
     */
    private String contacts;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号
     */
    private String account;
    /**
     * 税号
     */
    private String taxNumber;
    /**
     * 账单接收邮箱
     */
    private String billReceiveEmail;
    /**
     * 分销返利比(JSON数据)
     */
    private String partnerRatio;

    public BrandDetail() {
    }

    public BrandDetail(Brand brand) {
        this.brandId = brand.getId();
        this.brandName = brand.getName();
        this.designer = brand.getDesigners();
        this.status = 0;
        this.creator = brand.getCreator();
        this.contractStatus = 1;
        this.partnerRatio = PARTNER_RATIO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMerchantsMan() {
        return merchantsMan;
    }

    public void setMerchantsMan(String merchantsMan) {
        this.merchantsMan = merchantsMan;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBillReceiveEmail() {
        return billReceiveEmail;
    }

    public void setBillReceiveEmail(String billReceiveEmail) {
        this.billReceiveEmail = billReceiveEmail;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getPartnerRatio() {
        return partnerRatio;
    }

    public void setPartnerRatio(String partnerRatio) {
        this.partnerRatio = partnerRatio;
    }

    public JSONObject getPartnerRatioJson() {
        if (this.partnerRatio != null) {
            return JSON.parseObject(this.partnerRatio);
        }
        return JSON.parseObject(PARTNER_RATIO);
    }

}

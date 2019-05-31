package com.d2c.content.model;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -品牌入驻
 */
@Table(name = "v_brand_apply")
public class BrandApply extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 设计师名称（多个按，分割）
     */
    private String designers;
    /**
     * 联系人
     */
    private String contactor;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 邮件
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 品牌说明
     */
    private String intro;
    /**
     * 相关作品
     */
    private String works;
    /**
     * 1、已提交，8、处理完成
     */
    private Integer status = 1;
    /**
     * 微信
     */
    private String weixin;
    /**
     * 官网
     */
    private String website;
    /**
     * qq
     */
    private String qq;
    /**
     * 微博
     */
    private String weibo;
    /**
     * 价位段
     */
    private String priceSegment;
    /**
     * 目标客户人群
     */
    private String customerGroups;
    /**
     * 处理人
     */
    private String handler;
    /**
     * lookBook
     */
    private String lookBook;

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getPriceSegment() {
        return priceSegment;
    }

    public void setPriceSegment(String priceSegment) {
        this.priceSegment = priceSegment;
    }

    public String getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(String customerGroups) {
        this.customerGroups = customerGroups;
    }

    public String getLookBook() {
        return lookBook;
    }

    public void setLookBook(String lookBook) {
        this.lookBook = lookBook;
    }

    public JSONArray getLookBookToArray() {
        JSONArray arry = new JSONArray();
        if (lookBook != null && lookBook.length() > 0) {
            for (String item : lookBook.split(",")) {
                if (item == null) {
                    continue;
                }
                arry.add(item);
            }
        }
        return arry;
    }

}

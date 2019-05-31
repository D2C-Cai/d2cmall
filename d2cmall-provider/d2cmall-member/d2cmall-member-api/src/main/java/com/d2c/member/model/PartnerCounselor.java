package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 分销运营顾问
 */
@Table(name = "m_partner_counselor")
public class PartnerCounselor extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 顾问昵称
     */
    private String name;
    /**
     * 顾问头像
     */
    private String headPic;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 简要介绍
     */
    private String description;
    /**
     * 状态 0，下架；1 上架
     */
    private Integer status = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("headPic", this.getHeadPic());
        obj.put("qrCode", this.getQrCode());
        obj.put("weixin", this.getWeixin() == null ? "" : this.getWeixin());
        obj.put("description", this.getDescription() == null ? "" : this.getDescription());
        return obj;
    }

}
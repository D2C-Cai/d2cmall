package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 分销商店铺
 */
@Table(name = "m_partner_store")
public class PartnerStore extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 分销商ID
     */
    @AssertColumn("分销商ID不能为空")
    private Long partnerId;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String pic;
    /**
     * 简介
     */
    private String intro;
    /**
     * 背景
     */
    private String background;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("memberId", this.getMemberId());
        obj.put("partnerId", this.getPartnerId());
        obj.put("name", this.getName());
        obj.put("pic", this.getPic());
        obj.put("intro", this.getIntro());
        obj.put("background", this.getBackground());
        return obj;
    }

}
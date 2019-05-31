package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;

/**
 * 分销商邀请
 */
@Table(name = "m_partner_invite")
public class PartnerInvite extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 邀请人ID
     */
    private Long fromMemberId;
    /**
     * 邀请人分销ID
     */
    private Long fromPartnerId;
    /**
     * 邀请人昵称
     */
    private String fromNickname;
    /**
     * 邀请人头像
     */
    private String fromHeadPic;
    /**
     * 邀请人账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String fromLoginCode;
    /**
     * 邀请人等级
     */
    private Integer fromLevel;
    /**
     * 受邀人ID
     */
    private Long toMemberId;
    /**
     * 受邀人分销ID
     */
    private Long toPartnerId;
    /**
     * 受邀人昵称
     */
    private String toNickname;
    /**
     * 受邀人头像
     */
    private String toHeadPic;
    /**
     * 受邀人账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String toLoginCode;
    /**
     * 受邀人等级
     */
    private Integer toLevel;
    /**
     * 是否奖励 0：否，1：是，-1：不奖励
     */
    private Integer award = 0;

    public PartnerInvite() {
    }

    public PartnerInvite(Partner from, Partner to) {
        this.fromMemberId = from.getMemberId();
        this.fromPartnerId = from.getId();
        this.fromNickname = from.getName();
        this.fromHeadPic = from.getHeadPic();
        this.fromLoginCode = from.getLoginCode();
        this.fromLevel = from.getLevel();
        this.toMemberId = to.getMemberId();
        this.toPartnerId = to.getId();
        this.toNickname = to.getName();
        this.toHeadPic = to.getHeadPic();
        this.toLoginCode = to.getLoginCode();
        this.toLevel = to.getLevel();
    }

    public Long getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public Long getFromPartnerId() {
        return fromPartnerId;
    }

    public void setFromPartnerId(Long fromPartnerId) {
        this.fromPartnerId = fromPartnerId;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public String getFromHeadPic() {
        return fromHeadPic;
    }

    public void setFromHeadPic(String fromHeadPic) {
        this.fromHeadPic = fromHeadPic;
    }

    public String getFromLoginCode() {
        return fromLoginCode;
    }

    public void setFromLoginCode(String fromLoginCode) {
        this.fromLoginCode = fromLoginCode;
    }

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public Long getToPartnerId() {
        return toPartnerId;
    }

    public void setToPartnerId(Long toPartnerId) {
        this.toPartnerId = toPartnerId;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public String getToHeadPic() {
        return toHeadPic;
    }

    public void setToHeadPic(String toHeadPic) {
        this.toHeadPic = toHeadPic;
    }

    public String getToLoginCode() {
        return toLoginCode;
    }

    public void setToLoginCode(String toLoginCode) {
        this.toLoginCode = toLoginCode;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Integer getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(Integer fromLevel) {
        this.fromLevel = fromLevel;
    }

    public Integer getToLevel() {
        return toLevel;
    }

    public void setToLevel(Integer toLevel) {
        this.toLevel = toLevel;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("toMemberId", this.getToMemberId());
        obj.put("toPartnerId", this.getToPartnerId());
        obj.put("toLoginCode", this.getToLoginCode());
        obj.put("toNickname", this.getToNickname());
        obj.put("toHeadPic", this.getToHeadPic());
        return obj;
    }

}

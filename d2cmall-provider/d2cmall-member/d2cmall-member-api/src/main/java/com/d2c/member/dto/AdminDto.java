package com.d2c.member.dto;

import com.d2c.member.model.Admin;

import java.util.Date;
import java.util.List;

public class AdminDto extends Admin {

    private static final long serialVersionUID = 1L;
    /**
     * 角色
     */
    private List<String> roleValues;
    /**
     * 账号
     */
    private String displayName;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 同意协议时间
     */
    private Date agreeDate;

    public List<String> getRoleValues() {
        return roleValues;
    }

    public void setRoleValues(List<String> roleValues) {
        this.roleValues = roleValues;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getAgreeDate() {
        return agreeDate;
    }

    public void setAgreeDate(Date agreeDate) {
        this.agreeDate = agreeDate;
    }

}

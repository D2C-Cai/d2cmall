package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员等级申请
 */
@Table(name = "m_member_invite")
public class MemberInvite extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 等级ID
     */
    private Long levelId;
    /**
     * 等级名称
     */
    private String levelName;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 状态 -1已拒绝 0申请中 1已同意
     */
    private Integer status;
    /**
     * 渠道
     */
    private ChannelType type;
    /**
     * 拒绝原因
     */
    private String refuseReason;

    public MemberInvite() {
    }

    public MemberInvite(MemberInfo memberInfo, MemberLevel memberLevel) {
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.nickName = memberInfo.getNickname();
        this.levelId = memberLevel.getId();
        this.levelName = memberLevel.getName();
        this.level = memberLevel.getLevel();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public enum ChannelType {
        APPLY, INVITE
    }

}

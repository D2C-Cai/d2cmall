package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 拼团红包
 */
@Table(name = "o_share_red")
public class ShareRedPackets extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员Id
     */
    private Long memberId;
    /**
     * 头像
     */
    private String headPic;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 会员钱包Id
     */
    private Long accountId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 红包组Id
     */
    private Long groupId;
    /**
     * 是否是发起人
     */
    private int initiator;
    /**
     * 发起人
     */
    private Long initiatorMemberId;
    /**
     * 分到金额
     */
    private BigDecimal money;
    /**
     * 状态 0未瓜分 1是已瓜分
     */
    private Integer status;
    /**
     * 该会员是第几位人数
     */
    private Integer number;
    /**
     * 活动ID
     */
    private Long sharePromotionId;
    /**
     * 活动名称
     */
    private String sharePromotionName;

    public ShareRedPackets() {
    }

    public ShareRedPackets(ShareRedPacketsGroup shareRedPacketsGroup) {
        this.initiator = 0;
        this.status = 0;
        this.groupId = shareRedPacketsGroup.getId();
        this.number = shareRedPacketsGroup.getCurrentNumber() + 1;
        this.initiatorMemberId = shareRedPacketsGroup.getInitiatorMemberId();
        this.sharePromotionId = shareRedPacketsGroup.getSharePromotionId();
        this.sharePromotionName = shareRedPacketsGroup.getSharePromotionName();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getInitiator() {
        return initiator;
    }

    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(Long initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getSharePromotionId() {
        return sharePromotionId;
    }

    public void setSharePromotionId(Long sharePromotionId) {
        this.sharePromotionId = sharePromotionId;
    }

    public String getSharePromotionName() {
        return sharePromotionName;
    }

    public void setSharePromotionName(String sharePromotionName) {
        this.sharePromotionName = sharePromotionName;
    }

}

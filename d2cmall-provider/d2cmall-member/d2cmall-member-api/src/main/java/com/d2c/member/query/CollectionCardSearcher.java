package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class CollectionCardSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 卡片名称
     */
    private String cardName;
    /**
     * 定义ID
     */
    private Long defId;
    /**
     * 创建开始时间
     */
    private Date beginCreateDate;
    /**
     * 创建结束时间
     */
    private Date endCreateDate;
    private Boolean today;
    private Boolean fromSelf;
    /**
     * 活动ID
     */
    private Long promotionId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean isToday() {
        return today;
    }

    public void setToday(Boolean today) {
        this.today = today;
    }

    public Boolean isFromSelf() {
        return fromSelf;
    }

    public void setFromSelf(Boolean fromSelf) {
        this.fromSelf = fromSelf;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    /**
     * 今日是否自己抽过
     *
     * @param memberId
     * @return
     */
    public CollectionCardSearcher initMyTodaySearch(Long memberId) {
        this.setFromSelf(true);
        this.setMemberId(memberId);
        this.setToday(true);
        return this;
    }

}

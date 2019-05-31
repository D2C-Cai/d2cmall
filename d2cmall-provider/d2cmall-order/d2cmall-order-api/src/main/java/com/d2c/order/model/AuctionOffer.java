package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.MemberInfo;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 拍卖出价
 */
@Table(name = "o_auction_offer")
public class AuctionOffer extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 拍卖活动ID
     */
    private Long auctionId;
    /**
     * 拍卖活动标题
     */
    private String auctionTitle;
    /**
     * 拍卖商品ID
     */
    @AssertColumn("拍卖商品不能为空")
    private Long auctionProductId;
    /**
     * 会员ID
     */
    @AssertColumn("拍卖会员不能为空")
    private Long memberId;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String memberNick;
    /**
     * 出价
     */
    private BigDecimal offer;
    /**
     * 状态（-1 删除，0 出局，1 领先，8得标）
     */
    private Integer status = 1;

    public AuctionOffer() {
    }

    public AuctionOffer(MemberInfo memberInfo) {
        this.loginCode = memberInfo.getLoginCode();
        this.setMemberId(memberInfo.getId());
    }

    public Long getAuctionProductId() {
        return auctionProductId;
    }

    public void setAuctionProductId(Long auctionProductId) {
        this.auctionProductId = auctionProductId;
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

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getStatusText() {
        String text = "";
        if (this.status != null) {
            switch (this.status.intValue()) {
                case 0:
                    text = "出局";
                    break;
                case 1:
                    text = "领先";
                    break;
                case 8:
                    text = "得标";
                    break;
                case -1:
                    text = "删除";
                    break;
            }
        } else {
            text = "状态异常";
        }
        return text;
    }

}

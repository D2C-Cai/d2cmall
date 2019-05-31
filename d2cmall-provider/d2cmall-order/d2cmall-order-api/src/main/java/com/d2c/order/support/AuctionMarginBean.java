package com.d2c.order.support;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class AuctionMarginBean extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 保证金
     */
    public BigDecimal margin;
    /**
     * 支付流水号
     */
    public String paySn;
    /**
     * 拍卖活动ID
     */
    private Long auctionId;
    /**
     * 用户ID
     */
    private Long memberInfoId;
    /**
     * 保证金单号
     */
    private String marginSn;
    /**
     * 退款时间
     */
    private Date refundDate;
    /**
     * 退款人
     */
    private String refunder;
    /**
     * 退款方式
     */
    private String refundType;
    /**
     * 退款交易号
     */
    private String refundSn;

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public String getMarginSn() {
        return marginSn;
    }

    public void setMarginSn(String marginSn) {
        this.marginSn = marginSn;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefunder() {
        return refunder;
    }

    public void setRefunder(String refunder) {
        this.refunder = refunder;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

}

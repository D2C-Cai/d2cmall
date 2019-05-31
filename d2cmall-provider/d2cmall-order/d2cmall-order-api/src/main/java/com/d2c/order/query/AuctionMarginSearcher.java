package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class AuctionMarginSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 拍卖商品ID
     */
    private Long auctionProductId;
    /**
     * 拍卖活动ID
     */
    private Long auctionId;
    /**
     * 拍卖活动标题
     */
    private String auctionTitle;
    /**
     * 用户ID
     */
    private Long memberInfoId;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 保证金状态
     */
    private Integer status;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 退款人
     */
    private String refunder;
    /**
     * 退款开始时间
     */
    private Date startRefundDate;
    /**
     * 退款结束时间
     */
    private Date endRefundDate;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 订单编号
     */
    private String marginSn;

    public AuctionMarginSearcher() {
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public Long getAuctionProductId() {
        return auctionProductId;
    }

    public void setAuctionProductId(Long auctionProductId) {
        this.auctionProductId = auctionProductId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
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

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRefunder() {
        return refunder;
    }

    public void setRefunder(String refunder) {
        this.refunder = refunder;
    }

    public Date getStartRefundDate() {
        return startRefundDate;
    }

    public void setStartRefundDate(Date startRefundDate) {
        this.startRefundDate = startRefundDate;
    }

    public Date getEndRefundDate() {
        return endRefundDate;
    }

    public void setEndRefundDate(Date endRefundDate) {
        this.endRefundDate = endRefundDate;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getMarginSn() {
        return marginSn;
    }

    public void setMarginSn(String marginSn) {
        this.marginSn = marginSn;
    }

}

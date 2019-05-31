package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class AuctionOfferSearcher extends BaseQuery {

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
     * 开始时间
     */
    private Date beginCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;
    /**
     * 状态（-1 删除 ，0 出局，1 领先，8 竞拍成功）
     */
    private Integer status;

    public AuctionOfferSearcher() {
    }

    public Long getAuctionProductId() {
        return auctionProductId;
    }

    public void setAuctionProductId(Long auctionProductId) {
        this.auctionProductId = auctionProductId;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
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

}

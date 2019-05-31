package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拍卖商品
 */
@Table(name = "p_product_auction")
public class AuctionProduct extends RichTextBaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @AssertColumn
    private String title;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 规则说明
     */
    private String rule;
    /**
     * 0：未上架，1：已上架，-1：删除
     */
    private Integer status = 0;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 下架时间
     */
    private Date downMarketDate;
    /**
     * 起拍价
     */
    private BigDecimal beginPrice = new BigDecimal("0");
    /**
     * 阶梯追加金额
     */
    private BigDecimal stepPrice = new BigDecimal("0");
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 拍卖类型
     */
    private String type = "Auction";
    /**
     * 保证金
     */
    private BigDecimal margin = new BigDecimal("0");
    /**
     * 佣金
     */
    private BigDecimal expense = new BigDecimal("0");
    /**
     * 最后出价金额
     */
    private BigDecimal currentPrice = new BigDecimal("0");
    /**
     * 出价记录ID
     */
    private Long offerId;
    /**
     * 拍得人保证金ID
     */
    private Long auctionMarginId;
    /**
     * 拍得人ID
     */
    private Long memberId;
    /**
     * 出价次数
     */
    private Integer num = 0;
    /**
     * 排序
     */
    private Integer sort = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getDownMarketDate() {
        return downMarketDate;
    }

    public void setDownMarketDate(Date downMarketDate) {
        this.downMarketDate = downMarketDate;
    }

    public BigDecimal getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(BigDecimal beginPrice) {
        this.beginPrice = beginPrice;
    }

    public BigDecimal getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(BigDecimal stepPrice) {
        this.stepPrice = stepPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getAuctionMarginId() {
        return auctionMarginId;
    }

    public void setAuctionMarginId(Long auctionMarginId) {
        this.auctionMarginId = auctionMarginId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isDoing() {
        if (this.getStatus() == 1) {
            Date now = new Date();
            if (now.getTime() > beginDate.getTime() && now.getTime() < endDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getStatusName() {
        if (this.getStatus() == 1) {
            Date now = new Date();
            if (now.getTime() >= beginDate.getTime() && now.getTime() <= endDate.getTime()) {
                return "正在拍卖";
            } else if (now.getTime() < beginDate.getTime()) {
                return "即将拍卖";
            } else {
                return "拍卖结束";
            }
        } else {
            return "拍卖下架";
        }
    }

    public void setStatusName() {
    }

    public String getStatusString() {
        if (this.getStatus() == 1) {
            Date now = new Date();
            if (now.getTime() >= beginDate.getTime() && now.getTime() <= endDate.getTime()) {
                return "1";
            } else if (now.getTime() < beginDate.getTime()) {
                return "0";
            } else {
                return "2";
            }
        } else {
            return "-1";
        }
    }

    public void setStatusString() {
    }

}

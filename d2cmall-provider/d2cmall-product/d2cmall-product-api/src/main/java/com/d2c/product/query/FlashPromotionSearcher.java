package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class FlashPromotionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动场次
     */
    private String session;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 活动范围
     */
    private Integer promotionScope;
    /**
     * 活动类型
     */
    private String promotionType;
    /**
     * 开始时间
     */
    private Date begainStartDate;
    private Date begainEndDate;
    /**
     * 结束时间
     */
    private Date overStartDate;
    private Date overEndDate;
    /**
     * 渠道
     */
    private String channel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPromotionScope() {
        return promotionScope;
    }

    public void setPromotionScope(Integer promotionScope) {
        this.promotionScope = promotionScope;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Date getBegainStartDate() {
        return begainStartDate;
    }

    public void setBegainStartDate(Date begainStartDate) {
        this.begainStartDate = begainStartDate;
    }

    public Date getBegainEndDate() {
        return begainEndDate;
    }

    public void setBegainEndDate(Date begainEndDate) {
        this.begainEndDate = begainEndDate;
    }

    public Date getOverStartDate() {
        return overStartDate;
    }

    public void setOverStartDate(Date overStartDate) {
        this.overStartDate = overStartDate;
    }

    public Date getOverEndDate() {
        return overEndDate;
    }

    public void setOverEndDate(Date overEndDate) {
        this.overEndDate = overEndDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}

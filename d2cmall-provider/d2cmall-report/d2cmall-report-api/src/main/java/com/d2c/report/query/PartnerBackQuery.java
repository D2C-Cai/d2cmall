package com.d2c.report.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.annotation.search.SearchIgnore;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.report.query.base.BasePartnerQuery;

import java.util.Date;

public class PartnerBackQuery extends BasePartnerQuery {

    private static final long serialVersionUID = -5017213275436783402L;
    /**
     * 登录账号
     */
    @SearchField(oper = OperType.LIKE)
    private String loginCode;
    /**
     * 查询表类型 day： 查询买手日数据表 month： 查询买手月数据表
     */
    @SearchIgnore
    private String type;
    /**
     * 日期 yyyy-MM
     */
    @SearchField(oper = OperType.LIKE)
    private String month;
    /**
     * 日期 yyyy-MM-dd
     */
    @SearchField(oper = OperType.LIKE)
    private String day;
    /**
     * 标签
     */
    @SearchField(oper = OperType.LIKE)
    private String tags;
    /**
     * 邀请总人数
     */
    @SearchField(name = "inviteNum", oper = OperType.GTE)
    private Integer inviteNumMin;
    @SearchField(name = "inviteNum", oper = OperType.LTE)
    private Integer inviteNumMax;
    /**
     * 实付金额
     */
    @SearchField(name = "payAmount", oper = OperType.GTE)
    private Double payAmountMin;
    @SearchField(name = "payAmount", oper = OperType.LTE)
    private Double payAmountMax;
    /**
     * 返利金额
     */
    @SearchField(name = "payRebates", oper = OperType.GTE)
    private Double payRebatesMin;
    @SearchField(name = "payRebates", oper = OperType.LTE)
    private Double payRebatesMax;
    /**
     * 开始时间
     */
    @SearchField(name = "startTime", oper = OperType.GTE)
    private Date startTimeMin;
    @SearchField(name = "startTime", oper = OperType.LTE)
    private Date startTimeMax;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getInviteNumMin() {
        return inviteNumMin;
    }

    public void setInviteNumMin(Integer inviteNumMin) {
        this.inviteNumMin = inviteNumMin;
    }

    public Integer getInviteNumMax() {
        return inviteNumMax;
    }

    public void setInviteNumMax(Integer inviteNumMax) {
        this.inviteNumMax = inviteNumMax;
    }

    public Double getPayAmountMin() {
        return payAmountMin;
    }

    public void setPayAmountMin(Double payAmountMin) {
        this.payAmountMin = payAmountMin;
    }

    public Double getPayAmountMax() {
        return payAmountMax;
    }

    public void setPayAmountMax(Double payAmountMax) {
        this.payAmountMax = payAmountMax;
    }

    public Double getPayRebatesMin() {
        return payRebatesMin;
    }

    public void setPayRebatesMin(Double payRebatesMin) {
        this.payRebatesMin = payRebatesMin;
    }

    public Double getPayRebatesMax() {
        return payRebatesMax;
    }

    public void setPayRebatesMax(Double payRebatesMax) {
        this.payRebatesMax = payRebatesMax;
    }

    public Date getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(Date startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public Date getStartTimeMax() {
        return startTimeMax;
    }

    public void setStartTimeMax(Date startTimeMax) {
        this.startTimeMax = startTimeMax;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

}

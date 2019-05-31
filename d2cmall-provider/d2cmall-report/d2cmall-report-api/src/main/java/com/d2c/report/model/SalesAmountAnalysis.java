package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户下单和销售分析
 */
@Table(name = "rp_sales_amount_analysis")
public class SalesAmountAnalysis extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /** ------------------昨天------------------- */
    /**
     * 昨日下单用户数量
     */
    private Integer yesterdayBuyerCount;
    /**
     * 昨天下单金额
     */
    private BigDecimal yesterdaySalesAmount;
    /**
     * 昨日复购用户数量
     */
    private Integer yesterdayRebuyBuyerCount;
    /**
     * 昨日复购金额
     */
    private BigDecimal yesterdayRebuySalesAmount;
    /**
     * 昨日老用户首单数量
     */
    private Integer yesterdayOldCustomerBuyerCount;
    /**
     * 昨日老用户首单昨天金额
     */
    private BigDecimal yesterdayOldCustomerSalesAmount;
    /**
     * 昨日新用户首单数量
     */
    private Integer yesterdayNewCustomerBuyerCount;
    /**
     * 昨日新用户首单金额
     */
    private BigDecimal yesterdayNewCustomerSalesAmount;
    /** -----------------上周------------------- */
    /**
     * 上周下单用户数量
     */
    private Integer lastWeekBuyerCount;
    /**
     * 上周下单金额
     */
    private BigDecimal lastWeekSalesAmount;
    /**
     * 上周复购用户数量
     */
    private Integer lastWeekRebuyBuyerCount;
    /**
     * 上周复购金额
     */
    private BigDecimal lastWeekRebuySalesAmount;
    /**
     * 上周老用户首单数量
     */
    private Integer lastWeekOldCustomerBuyerCount;
    /**
     * 上周老用户首单昨天金额
     */
    private BigDecimal lastWeekOldCustomerSalesAmount;
    /**
     * 上周新用户首单数量
     */
    private Integer lastWeekNewCustomerBuyerCount;
    /**
     * 上周新用户首单金额
     */
    private BigDecimal lastWeekNewCustomerSalesAmount;
    /** -----------------上个月------------------- */
    /**
     * 上个月下单用户数量
     */
    private Integer lastMonthBuyerCount;
    /**
     * 上个月下单金额
     */
    private BigDecimal lastMonthSalesAmount;
    /**
     * 上个月复购用户数量
     */
    private Integer lastMonthRebuyBuyerCount;
    /**
     * 上个月复购金额
     */
    private BigDecimal lastMonthRebuySalesAmount;
    /**
     * 上个月老用户首单数量
     */
    private Integer lastMonthOldCustomerBuyerCount;
    /**
     * 上个月老用户首单昨天金额
     */
    private BigDecimal lastMonthOldCustomerSalesAmount;
    /**
     * 上个月新用户首单数量
     */
    private Integer lastMonthNewCustomerBuyerCount;
    /**
     * 上个月新用户首单金额
     */
    private BigDecimal lastMonthNewCustomerSalesAmount;
    /** -------------------上个季度------------------------ **/
    /**
     * 上季度下单用户数量
     */
    private Integer lastSeasonBuyerCount;
    /**
     * 上季度下单金额
     */
    private BigDecimal lastSeasonSalesAmount;
    /**
     * 上季度复购用户数量
     */
    private Integer lastSeasonRebuyBuyerCount;
    /**
     * 上季度复购金额
     */
    private BigDecimal lastSeasonRebuySalesAmount;
    /**
     * 上季度老用户首单数量
     */
    private Integer lastSeasonOldCustomerBuyerCount;
    /**
     * 上季度老用户首单昨天金额
     */
    private BigDecimal lastSeasonOldCustomerSalesAmount;
    /**
     * 上季度新用户首单数量
     */
    private Integer lastSeasonNewCustomerBuyerCount;
    /**
     * 上季度新用户首单金额
     */
    private BigDecimal lastSeasonNewCustomerSalesAmount;
    /** ------------------去年------------------------ **/
    /**
     * 去年下单用户数量
     */
    private Integer lastYearBuyerCount;
    /**
     * 去年下单金额
     */
    private BigDecimal lastYearSalesAmount;
    /**
     * 去年复购用户数量
     */
    private Integer lastYearRebuyBuyerCount;
    /**
     * 去年复购金额
     */
    private BigDecimal lastYearRebuySalesAmount;
    /**
     * 去年老用户首单数量
     */
    private Integer lastYearOldCustomerBuyerCount;
    /**
     * 去年老用户首单昨天金额
     */
    private BigDecimal lastYearOldCustomerSalesAmount;
    /**
     * 去年新用户首单数量
     */
    private Integer lastYearNewCustomerBuyerCount;
    /**
     * 去年新用户首单金额
     */
    private BigDecimal lastYearNewCustomerSalesAmount;

    public Integer getYesterdayBuyerCount() {
        return yesterdayBuyerCount;
    }

    public void setYesterdayBuyerCount(Integer yesterdayBuyerCount) {
        this.yesterdayBuyerCount = yesterdayBuyerCount;
    }

    public BigDecimal getYesterdaySalesAmount() {
        return yesterdaySalesAmount;
    }

    public void setYesterdaySalesAmount(BigDecimal yesterdaySalesAmount) {
        this.yesterdaySalesAmount = yesterdaySalesAmount;
    }

    public Integer getYesterdayRebuyBuyerCount() {
        return yesterdayRebuyBuyerCount;
    }

    public void setYesterdayRebuyBuyerCount(Integer yesterdayRebuyBuyerCount) {
        this.yesterdayRebuyBuyerCount = yesterdayRebuyBuyerCount;
    }

    public BigDecimal getYesterdayRebuySalesAmount() {
        return yesterdayRebuySalesAmount;
    }

    public void setYesterdayRebuySalesAmount(BigDecimal yesterdayRebuySalesAmount) {
        this.yesterdayRebuySalesAmount = yesterdayRebuySalesAmount;
    }

    public Integer getYesterdayOldCustomerBuyerCount() {
        return yesterdayOldCustomerBuyerCount;
    }

    public void setYesterdayOldCustomerBuyerCount(Integer yesterdayOldCustomerBuyerCount) {
        this.yesterdayOldCustomerBuyerCount = yesterdayOldCustomerBuyerCount;
    }

    public BigDecimal getYesterdayOldCustomerSalesAmount() {
        return yesterdayOldCustomerSalesAmount;
    }

    public void setYesterdayOldCustomerSalesAmount(BigDecimal yesterdayOldCustomerSalesAmount) {
        this.yesterdayOldCustomerSalesAmount = yesterdayOldCustomerSalesAmount;
    }

    public Integer getYesterdayNewCustomerBuyerCount() {
        return yesterdayNewCustomerBuyerCount;
    }

    public void setYesterdayNewCustomerBuyerCount(Integer yesterdayNewCustomerBuyerCount) {
        this.yesterdayNewCustomerBuyerCount = yesterdayNewCustomerBuyerCount;
    }

    public BigDecimal getYesterdayNewCustomerSalesAmount() {
        return yesterdayNewCustomerSalesAmount;
    }

    public void setYesterdayNewCustomerSalesAmount(BigDecimal yesterdayNewCustomerSalesAmount) {
        this.yesterdayNewCustomerSalesAmount = yesterdayNewCustomerSalesAmount;
    }

    public Integer getLastWeekBuyerCount() {
        return lastWeekBuyerCount;
    }

    public void setLastWeekBuyerCount(Integer lastWeekBuyerCount) {
        this.lastWeekBuyerCount = lastWeekBuyerCount;
    }

    public BigDecimal getLastWeekSalesAmount() {
        return lastWeekSalesAmount;
    }

    public void setLastWeekSalesAmount(BigDecimal lastWeekSalesAmount) {
        this.lastWeekSalesAmount = lastWeekSalesAmount;
    }

    public Integer getLastWeekRebuyBuyerCount() {
        return lastWeekRebuyBuyerCount;
    }

    public void setLastWeekRebuyBuyerCount(Integer lastWeekRebuyBuyerCount) {
        this.lastWeekRebuyBuyerCount = lastWeekRebuyBuyerCount;
    }

    public BigDecimal getLastWeekRebuySalesAmount() {
        return lastWeekRebuySalesAmount;
    }

    public void setLastWeekRebuySalesAmount(BigDecimal lastWeekRebuySalesAmount) {
        this.lastWeekRebuySalesAmount = lastWeekRebuySalesAmount;
    }

    public Integer getLastWeekOldCustomerBuyerCount() {
        return lastWeekOldCustomerBuyerCount;
    }

    public void setLastWeekOldCustomerBuyerCount(Integer lastWeekOldCustomerBuyerCount) {
        this.lastWeekOldCustomerBuyerCount = lastWeekOldCustomerBuyerCount;
    }

    public BigDecimal getLastWeekOldCustomerSalesAmount() {
        return lastWeekOldCustomerSalesAmount;
    }

    public void setLastWeekOldCustomerSalesAmount(BigDecimal lastWeekOldCustomerSalesAmount) {
        this.lastWeekOldCustomerSalesAmount = lastWeekOldCustomerSalesAmount;
    }

    public Integer getLastWeekNewCustomerBuyerCount() {
        return lastWeekNewCustomerBuyerCount;
    }

    public void setLastWeekNewCustomerBuyerCount(Integer lastWeekNewCustomerBuyerCount) {
        this.lastWeekNewCustomerBuyerCount = lastWeekNewCustomerBuyerCount;
    }

    public BigDecimal getLastWeekNewCustomerSalesAmount() {
        return lastWeekNewCustomerSalesAmount;
    }

    public void setLastWeekNewCustomerSalesAmount(BigDecimal lastWeekNewCustomerSalesAmount) {
        this.lastWeekNewCustomerSalesAmount = lastWeekNewCustomerSalesAmount;
    }

    public Integer getLastMonthBuyerCount() {
        return lastMonthBuyerCount;
    }

    public void setLastMonthBuyerCount(Integer lastMonthBuyerCount) {
        this.lastMonthBuyerCount = lastMonthBuyerCount;
    }

    public BigDecimal getLastMonthSalesAmount() {
        return lastMonthSalesAmount;
    }

    public void setLastMonthSalesAmount(BigDecimal lastMonthSalesAmount) {
        this.lastMonthSalesAmount = lastMonthSalesAmount;
    }

    public Integer getLastMonthRebuyBuyerCount() {
        return lastMonthRebuyBuyerCount;
    }

    public void setLastMonthRebuyBuyerCount(Integer lastMonthRebuyBuyerCount) {
        this.lastMonthRebuyBuyerCount = lastMonthRebuyBuyerCount;
    }

    public BigDecimal getLastMonthRebuySalesAmount() {
        return lastMonthRebuySalesAmount;
    }

    public void setLastMonthRebuySalesAmount(BigDecimal lastMonthRebuySalesAmount) {
        this.lastMonthRebuySalesAmount = lastMonthRebuySalesAmount;
    }

    public Integer getLastMonthOldCustomerBuyerCount() {
        return lastMonthOldCustomerBuyerCount;
    }

    public void setLastMonthOldCustomerBuyerCount(Integer lastMonthOldCustomerBuyerCount) {
        this.lastMonthOldCustomerBuyerCount = lastMonthOldCustomerBuyerCount;
    }

    public BigDecimal getLastMonthOldCustomerSalesAmount() {
        return lastMonthOldCustomerSalesAmount;
    }

    public void setLastMonthOldCustomerSalesAmount(BigDecimal lastMonthOldCustomerSalesAmount) {
        this.lastMonthOldCustomerSalesAmount = lastMonthOldCustomerSalesAmount;
    }

    public Integer getLastMonthNewCustomerBuyerCount() {
        return lastMonthNewCustomerBuyerCount;
    }

    public void setLastMonthNewCustomerBuyerCount(Integer lastMonthNewCustomerBuyerCount) {
        this.lastMonthNewCustomerBuyerCount = lastMonthNewCustomerBuyerCount;
    }

    public BigDecimal getLastMonthNewCustomerSalesAmount() {
        return lastMonthNewCustomerSalesAmount;
    }

    public void setLastMonthNewCustomerSalesAmount(BigDecimal lastMonthNewCustomerSalesAmount) {
        this.lastMonthNewCustomerSalesAmount = lastMonthNewCustomerSalesAmount;
    }

    public Integer getLastSeasonBuyerCount() {
        return lastSeasonBuyerCount;
    }

    public void setLastSeasonBuyerCount(Integer lastSeasonBuyerCount) {
        this.lastSeasonBuyerCount = lastSeasonBuyerCount;
    }

    public BigDecimal getLastSeasonSalesAmount() {
        return lastSeasonSalesAmount;
    }

    public void setLastSeasonSalesAmount(BigDecimal lastSeasonSalesAmount) {
        this.lastSeasonSalesAmount = lastSeasonSalesAmount;
    }

    public Integer getLastSeasonRebuyBuyerCount() {
        return lastSeasonRebuyBuyerCount;
    }

    public void setLastSeasonRebuyBuyerCount(Integer lastSeasonRebuyBuyerCount) {
        this.lastSeasonRebuyBuyerCount = lastSeasonRebuyBuyerCount;
    }

    public BigDecimal getLastSeasonRebuySalesAmount() {
        return lastSeasonRebuySalesAmount;
    }

    public void setLastSeasonRebuySalesAmount(BigDecimal lastSeasonRebuySalesAmount) {
        this.lastSeasonRebuySalesAmount = lastSeasonRebuySalesAmount;
    }

    public Integer getLastSeasonOldCustomerBuyerCount() {
        return lastSeasonOldCustomerBuyerCount;
    }

    public void setLastSeasonOldCustomerBuyerCount(Integer lastSeasonOldCustomerBuyerCount) {
        this.lastSeasonOldCustomerBuyerCount = lastSeasonOldCustomerBuyerCount;
    }

    public BigDecimal getLastSeasonOldCustomerSalesAmount() {
        return lastSeasonOldCustomerSalesAmount;
    }

    public void setLastSeasonOldCustomerSalesAmount(BigDecimal lastSeasonOldCustomerSalesAmount) {
        this.lastSeasonOldCustomerSalesAmount = lastSeasonOldCustomerSalesAmount;
    }

    public Integer getLastSeasonNewCustomerBuyerCount() {
        return lastSeasonNewCustomerBuyerCount;
    }

    public void setLastSeasonNewCustomerBuyerCount(Integer lastSeasonNewCustomerBuyerCount) {
        this.lastSeasonNewCustomerBuyerCount = lastSeasonNewCustomerBuyerCount;
    }

    public BigDecimal getLastSeasonNewCustomerSalesAmount() {
        return lastSeasonNewCustomerSalesAmount;
    }

    public void setLastSeasonNewCustomerSalesAmount(BigDecimal lastSeasonNewCustomerSalesAmount) {
        this.lastSeasonNewCustomerSalesAmount = lastSeasonNewCustomerSalesAmount;
    }

    public Integer getLastYearBuyerCount() {
        return lastYearBuyerCount;
    }

    public void setLastYearBuyerCount(Integer lastYearBuyerCount) {
        this.lastYearBuyerCount = lastYearBuyerCount;
    }

    public BigDecimal getLastYearSalesAmount() {
        return lastYearSalesAmount;
    }

    public void setLastYearSalesAmount(BigDecimal lastYearSalesAmount) {
        this.lastYearSalesAmount = lastYearSalesAmount;
    }

    public Integer getLastYearRebuyBuyerCount() {
        return lastYearRebuyBuyerCount;
    }

    public void setLastYearRebuyBuyerCount(Integer lastYearRebuyBuyerCount) {
        this.lastYearRebuyBuyerCount = lastYearRebuyBuyerCount;
    }

    public BigDecimal getLastYearRebuySalesAmount() {
        return lastYearRebuySalesAmount;
    }

    public void setLastYearRebuySalesAmount(BigDecimal lastYearRebuySalesAmount) {
        this.lastYearRebuySalesAmount = lastYearRebuySalesAmount;
    }

    public Integer getLastYearOldCustomerBuyerCount() {
        return lastYearOldCustomerBuyerCount;
    }

    public void setLastYearOldCustomerBuyerCount(Integer lastYearOldCustomerBuyerCount) {
        this.lastYearOldCustomerBuyerCount = lastYearOldCustomerBuyerCount;
    }

    public BigDecimal getLastYearOldCustomerSalesAmount() {
        return lastYearOldCustomerSalesAmount;
    }

    public void setLastYearOldCustomerSalesAmount(BigDecimal lastYearOldCustomerSalesAmount) {
        this.lastYearOldCustomerSalesAmount = lastYearOldCustomerSalesAmount;
    }

    public Integer getLastYearNewCustomerBuyerCount() {
        return lastYearNewCustomerBuyerCount;
    }

    public void setLastYearNewCustomerBuyerCount(Integer lastYearNewCustomerBuyerCount) {
        this.lastYearNewCustomerBuyerCount = lastYearNewCustomerBuyerCount;
    }

    public BigDecimal getLastYearNewCustomerSalesAmount() {
        return lastYearNewCustomerSalesAmount;
    }

    public void setLastYearNewCustomerSalesAmount(BigDecimal lastYearNewCustomerSalesAmount) {
        this.lastYearNewCustomerSalesAmount = lastYearNewCustomerSalesAmount;
    }

}

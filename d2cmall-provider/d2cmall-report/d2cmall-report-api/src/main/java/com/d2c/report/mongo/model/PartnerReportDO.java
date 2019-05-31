package com.d2c.report.mongo.model;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.model.BaseMongoDO;

import java.util.Date;
import java.util.Map;

/**
 * 买手每日汇总统计报表
 *
 * @author wull
 */
public class PartnerReportDO extends BaseMongoDO {

    private static final long serialVersionUID = -716851315034758521L;
    /**
     * 报表统计时间
     */
    private Date statDate;
    /**
     * 报表统计日期
     */
    private String day;
    /**
     * 所有买手店数量
     * <p> 状态 0.试用期 1.正常-1.未通过 -9.永久关闭
     */
    private Long count;
    /**
     * 现存买手店数量, 状态为 0.试用期 1.正常
     */
    private Long liveCount;
    /**
     * 开单店铺数量, 有销售额的店铺
     */
    private Long saleCount;
    /**
     * 有效买手店数量, 实付金额 >= 1000
     */
    private Long validCount;
    /**
     * 试用期AM店铺数量
     */
    private Long tryAMCount;
    /**
     * 有销售的AM店铺数量
     */
    private Long saleAMCount;
    /**
     * 试用期DM店铺数量
     */
    private Long tryDMCount;
    /**
     * 有销售的DM店铺数量
     */
    private Long saleDMCount;
    /**
     * 试用期买手店铺数量
     */
    private Long tryPartnerCount;
    /**
     * 有销售的买手店铺数量, 实付金额大于 0
     */
    private Long salePartnerCount;
    /**
     * 有效的买手店铺数量, 实付金额大于 1000
     */
    private Long validPartnerCount;
    /**
     * 日新增买手店数量
     */
    private Long dayAdd;
    /**
     * 月新增买手店数量
     */
    private Long monthAdd;
    /**
     * 日销售额
     */
    private Double dayPayAmount;
    /**
     * 月销售额
     */
    private Double monthPayAmount;
    /**
     * 总销售额
     */
    private Double payAmount;
    /**
     * 绑定sv数量
     */
    private Long svCount;
    /**
     * 未绑定sv的开单店铺数量
     */
    private Long noSvSaleCount;
    /**
     * 未绑定sv的关店铺数量
     */
    private Long noSvCloseCount;
    /**
     * 多日闭店数据统计， key 为还有几日闭店
     */
    private Map<Integer, Long> dayCloseCounts;
    /**
     * 月闭店统计
     */
    private Long monthCloseCount;

    public PartnerReportDO() {
    }

    public PartnerReportDO(Date statDate) {
        this.statDate = statDate;
        this.day = DateUt.date2str(statDate);
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(Long liveCount) {
        this.liveCount = liveCount;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public Long getValidCount() {
        return validCount;
    }

    public void setValidCount(Long validCount) {
        this.validCount = validCount;
    }

    public Long getDayAdd() {
        return dayAdd;
    }

    public void setDayAdd(Long dayAdd) {
        this.dayAdd = dayAdd;
    }

    public Long getMonthAdd() {
        return monthAdd;
    }

    public void setMonthAdd(Long monthAdd) {
        this.monthAdd = monthAdd;
    }

    public Double getDayPayAmount() {
        return dayPayAmount;
    }

    public void setDayPayAmount(Double dayPayAmount) {
        this.dayPayAmount = dayPayAmount;
    }

    public Double getMonthPayAmount() {
        return monthPayAmount;
    }

    public void setMonthPayAmount(Double monthPayAmount) {
        this.monthPayAmount = monthPayAmount;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Long getSvCount() {
        return svCount;
    }

    public void setSvCount(Long svCount) {
        this.svCount = svCount;
    }

    public Long getNoSvSaleCount() {
        return noSvSaleCount;
    }

    public void setNoSvSaleCount(Long noSvSaleCount) {
        this.noSvSaleCount = noSvSaleCount;
    }

    public Long getNoSvCloseCount() {
        return noSvCloseCount;
    }

    public void setNoSvCloseCount(Long noSvCloseCount) {
        this.noSvCloseCount = noSvCloseCount;
    }

    public Map<Integer, Long> getDayCloseCounts() {
        return dayCloseCounts;
    }

    public void setDayCloseCounts(Map<Integer, Long> dayCloseCounts) {
        this.dayCloseCounts = dayCloseCounts;
    }

    public Long getMonthCloseCount() {
        return monthCloseCount;
    }

    public void setMonthCloseCount(Long monthCloseCount) {
        this.monthCloseCount = monthCloseCount;
    }

    public Long getTryAMCount() {
        return tryAMCount;
    }

    public void setTryAMCount(Long tryAMCount) {
        this.tryAMCount = tryAMCount;
    }

    public Long getSaleAMCount() {
        return saleAMCount;
    }

    public void setSaleAMCount(Long saleAMCount) {
        this.saleAMCount = saleAMCount;
    }

    public Long getTryDMCount() {
        return tryDMCount;
    }

    public void setTryDMCount(Long tryDMCount) {
        this.tryDMCount = tryDMCount;
    }

    public Long getSaleDMCount() {
        return saleDMCount;
    }

    public void setSaleDMCount(Long saleDMCount) {
        this.saleDMCount = saleDMCount;
    }

    public Long getTryPartnerCount() {
        return tryPartnerCount;
    }

    public void setTryPartnerCount(Long tryPartnerCount) {
        this.tryPartnerCount = tryPartnerCount;
    }

    public Long getSalePartnerCount() {
        return salePartnerCount;
    }

    public void setSalePartnerCount(Long salePartnerCount) {
        this.salePartnerCount = salePartnerCount;
    }

    public Long getValidPartnerCount() {
        return validPartnerCount;
    }

    public void setValidPartnerCount(Long validPartnerCount) {
        this.validPartnerCount = validPartnerCount;
    }

}

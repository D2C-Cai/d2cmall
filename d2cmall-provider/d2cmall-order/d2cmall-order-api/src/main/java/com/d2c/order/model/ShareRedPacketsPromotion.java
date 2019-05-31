package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拼团红包活动
 */
@Table(name = "p_share_red_prmotion")
public class ShareRedPacketsPromotion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    @AssertColumn("活动名称不能为空")
    private String name;
    /**
     * 活动开始时间
     */
    @AssertColumn("开始时间不能为空")
    private Date beginDate;
    /**
     * 活动结束时间
     */
    @AssertColumn("结束时间不能为空")
    private Date endDate;
    /**
     * 成团人数
     */
    @AssertColumn("成团人数不能为空")
    private BigDecimal maxNumber;
    /**
     * 成团总金额
     */
    @AssertColumn("红包总金额不能为空")
    private BigDecimal totalMoney;
    /**
     * 最小分到的金额
     */
    @AssertColumn("最小分到的金额不能为空")
    private BigDecimal minMoney;
    /**
     * 最大分到的金额
     */
    @AssertColumn("最大分到的金额不能为空")
    private BigDecimal maxMoney;
    /**
     * 活动状态
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(BigDecimal maxNumber) {
        this.maxNumber = maxNumber;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

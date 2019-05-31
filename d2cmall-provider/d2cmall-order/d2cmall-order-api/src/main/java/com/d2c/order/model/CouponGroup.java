package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 优惠券包
 */
@Table(name = "o_coupon_group")
public class CouponGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 优惠券组ID
     */
    @AssertColumn("优惠券组ID不能为空")
    private Long groupId;
    /**
     * 数量
     */
    private Integer num = 1;
    /**
     * 面额
     */
    private Integer amount = 0;
    /**
     * 满XX减
     */
    private Integer needAmount = 0;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 抵用券数量
     */
    @Transient
    private Integer totalNum1 = 0;
    /**
     * 折扣券数量
     */
    @Transient
    private Integer totalNum2 = 0;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getTotalNum1() {
        return totalNum1;
    }

    public void setTotalNum1(Integer totalNum1) {
        this.totalNum1 = totalNum1;
    }

    public Integer getTotalNum2() {
        return totalNum2;
    }

    public void setTotalNum2(Integer totalNum2) {
        this.totalNum2 = totalNum2;
    }

}
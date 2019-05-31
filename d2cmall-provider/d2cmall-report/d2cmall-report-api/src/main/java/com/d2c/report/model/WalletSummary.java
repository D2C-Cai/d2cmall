package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "rp_wallet_summary")
public class WalletSummary extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 统计日期
     */
    private Date calculateDate;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 实际支出金额（可兑换金额部分）
     */
    private BigDecimal amount = new BigDecimal(0);
    /**
     * 支出红包金额
     */
    private BigDecimal giftAmount = new BigDecimal(0);
    /**
     * 财务类型
     */
    private String payType;
    /**
     * 收入/支出
     */
    private Integer direction = 1;

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Integer getPaymentType() {
        return 7;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTransactionInfo() {
        if (businessType.equals("钱包总额")) {
            return this.businessType;
        }
        return BusinessTypeEnum.valueOf(businessType).getDisplay() + PayTypeEnum.valueOf(payType).getDisplay();
    }

}

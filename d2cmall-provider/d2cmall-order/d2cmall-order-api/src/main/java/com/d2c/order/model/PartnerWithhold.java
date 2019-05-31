package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 分销导入扣款
 *
 * @author Lain
 */
@Table(name = "o_partner_withhold")
public class PartnerWithhold extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 分销ID
     */
    private Long partnerId;
    /**
     * 扣款金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String memo;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}

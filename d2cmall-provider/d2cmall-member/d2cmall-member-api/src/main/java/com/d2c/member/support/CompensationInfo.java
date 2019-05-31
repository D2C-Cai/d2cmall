package com.d2c.member.support;

import java.math.BigDecimal;

public class CompensationInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 赔偿金额
     */
    private BigDecimal compensation;
    /**
     * 赔偿类型
     */
    private int type;

    public CompensationInfo() {
        super();
    }

    public CompensationInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

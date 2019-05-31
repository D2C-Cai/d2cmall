package com.d2c.order.enums;

import java.math.BigDecimal;

public enum TaxRule2StepEnum {
    STEP1(0, 30000) {
        @Override
        public BigDecimal formula(BigDecimal count) {
            return new BigDecimal(count.doubleValue() * 0.006).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    },
    STEP2(30000, Integer.MAX_VALUE) {
        @Override
        public BigDecimal formula(BigDecimal count) {
            return new BigDecimal(count.doubleValue() * 0.0396).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    };
    private int closeStart;
    private int openEnd;

    TaxRule2StepEnum(int closeStart, int openEnd) {
        this.closeStart = closeStart;
        this.openEnd = openEnd;
    }

    /**
     * 计算提现金额应缴的税款
     *
     * @param count
     * @return
     */
    public static BigDecimal calculateTax(BigDecimal count) {
        for (TaxRule2StepEnum item : TaxRule2StepEnum.values()) {
            if (count.compareTo(new BigDecimal(item.getCloseStart())) >= 0
                    && count.compareTo(new BigDecimal(item.getOpenEnd())) < 0) {
                return item.formula(count);
            }
        }
        return new BigDecimal(0);
    }

    public BigDecimal formula(BigDecimal count) {
        return new BigDecimal(0);
    }

    public int getCloseStart() {
        return closeStart;
    }

    public void setCloseStart(int closeStart) {
        this.closeStart = closeStart;
    }

    public int getOpenEnd() {
        return openEnd;
    }

    public void setOpenEnd(int openEnd) {
        this.openEnd = openEnd;
    }
}

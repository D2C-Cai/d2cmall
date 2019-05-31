package com.d2c.member.dto;

import com.d2c.member.model.Partner;

public class PartnerDto extends Partner {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer billCount;

    public Integer getBillCount() {
        return billCount;
    }

    public void setBillCount(Integer billCount) {
        this.billCount = billCount;
    }

}

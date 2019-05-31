package com.d2c.member.support;

public class PartnerBillInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    private String loginCode;

    public PartnerBillInfo() {
        super();
    }

    public PartnerBillInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

}

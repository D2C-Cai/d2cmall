package com.d2c.order.enums;

import java.util.HashMap;
import java.util.Map;

public enum PaymentTypeEnum {
    ALIPAY(1, "支付宝"), COD(3, "货到付款"), WALLET(7, "钱包支付"), FREEPAY(11, "免支付"),
    WXPAY(6, "微信JS支付"), WX_SCANPAY(8, "微信扫码支付"), WXAPPPAY(9, "微信APP支付"),
    CARDPAY(10, "D2C卡支付"), MIMEPAY(13, "分期支付"), HBPAY(15, "花呗支付"),
    CASH(12, "现金支付"), OFFLINE(18, "线下支付"), COMMPAY(5, "交通银行"), TENPAY(2, "财付通"), PAYPAL(14, "贝宝支付");
    private static Map<Integer, PaymentTypeEnum> holder = new HashMap<>();

    static {
        for (PaymentTypeEnum paymentType : values()) {
            holder.put(paymentType.getCode(), paymentType);
        }
    }

    private int code;
    private String display;

    PaymentTypeEnum(int code, String display) {
        this.code = code;
        this.display = display;
    }

    public static PaymentTypeEnum getByCode(Integer i) {
        return holder.get(i);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}

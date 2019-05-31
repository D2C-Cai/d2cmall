package com.d2c.order.dto;

import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CouponOrder;
import com.d2c.order.model.base.IPaymentInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CouponOrderDto extends CouponOrder implements IPaymentInterface {

    private static final long serialVersionUID = 1L;

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return this.getOrderSn();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.COUPON.name();
    }

    @Override
    public String getBillSubject() {
        return this.getCouponName();
    }

    @Override
    public String getBillBody() {
        return this.getCouponName();
    }

    @Override
    public BigDecimal getBillTotalFee() {
        return this.getTotalAmount();
    }

    @Override
    public BigDecimal getBillShipFee() {
        return new BigDecimal(0);
    }

    @Override
    public Long getPaymentId() {
        return super.getPaymentId();
    }

    @Override
    public Long getMemberId() {
        return super.getMemberId();
    }

    @Override
    public Long getToMemberId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWaitPay() {
        return this.getOrderStatus() != null && this.getOrderStatus() == 0;
    }

    @Override
    public boolean getCross() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, String> getPayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("billId_", String.valueOf(this.getBillSourceId()));
        map.put("billSn_", String.valueOf(this.getBillSourceSn()));
        map.put("billType_", String.valueOf(this.getBillSourceType()));
        map.put("billSubject_", String.valueOf(this.getBillSubject()));
        map.put("billTotalFee_", String.valueOf(this.getBillTotalFee()));
        map.put("backUrl_", "/");
        map.put("goodsType_", "0");
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET;
        map.put("billPayTypes_", payType);
        return map;
    }

}

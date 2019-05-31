package com.d2c.order.dto;

import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.model.base.IPaymentInterface;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PresentOrderDto extends PresentOrder implements IPaymentInterface {

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
        return OrderTypeEnum.PRESENT.name();
    }

    @Override
    public String getBillSubject() {
        return this.getProductName() + "礼物支付";
    }

    @Override
    public String getBillBody() {
        return this.getProductName() + "礼物支付";
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
        return this.getBuyMemberInfoId();
    }

    @Override
    public Long getToMemberId() {
        return this.getReceiveMemberInfoId();
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
    @JsonIgnore
    public Map<String, String> getPayParams() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("billId_", String.valueOf(this.getBillSourceId()));
        map.put("billSn_", String.valueOf(this.getBillSourceSn()));
        map.put("billType_", String.valueOf(this.getBillSourceType()));
        map.put("billSubject_", String.valueOf(this.getBillSubject()));
        map.put("billTotalFee_", String.valueOf(this.getBillTotalFee()));
        map.put("backUrl_", "/");
        map.put("goodsType_", "0");
        String payType = PaymentTypeEnum.WALLET + "";
        map.put("billPayTypes_", payType);
        return map;
    }

}

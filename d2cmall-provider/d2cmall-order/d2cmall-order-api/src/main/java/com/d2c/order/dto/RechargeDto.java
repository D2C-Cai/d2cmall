package com.d2c.order.dto;

import com.d2c.member.model.Account;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Recharge;
import com.d2c.order.model.base.IPaymentInterface;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RechargeDto extends Recharge implements IPaymentInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 钱包账号
     */
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return this.getSn();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.PAY.name();
    }

    @Override
    public String getBillSubject() {
        return "D2C钱包在线充值" + this.getRechargeAmount() + "元";
    }

    @Override
    public String getBillBody() {
        return "D2C钱包在线充值" + this.getRechargeAmount() + "元";
    }

    @Override
    public BigDecimal getBillTotalFee() {
        return this.getRechargeAmount();
    }

    @Override
    public BigDecimal getBillShipFee() {
        return new BigDecimal(0);
    }

    @Override
    public Long getPaymentId() {
        // TODO Auto-generated method stub
        return null;
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
        return this.getStatus() != null && this.getStatus() == -2;
    }

    @Override
    public boolean getCross() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @JsonIgnore
    public Map<String, String> getPayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("billId_", String.valueOf(this.getBillSourceId()));
        map.put("billSn_", String.valueOf(this.getBillSourceSn()));
        map.put("billType_", String.valueOf(this.getBillSourceType()));
        map.put("billSubject_", String.valueOf(this.getBillSubject()));
        map.put("billTotalFee_", String.valueOf(this.getBillTotalFee()));
        map.put("backUrl_", "/");
        map.put("goodsType_", "0");
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY;
        map.put("billPayTypes_", payType);
        return map;
    }

}

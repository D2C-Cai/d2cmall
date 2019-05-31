package com.d2c.order.dto;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.model.base.IPaymentInterface;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class BarrageDto extends PreUserDO implements IPaymentInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 买家ID
     */
    private Long buyMemberInfoId;
    /**
     * 接收人ID
     */
    private Long receiveMemberInfoId;
    /**
     * 弹幕总价
     */
    private BigDecimal totalAmount;

    public Long getBuyMemberInfoId() {
        return buyMemberInfoId;
    }

    public void setBuyMemberInfoId(Long buyMemberInfoId) {
        this.buyMemberInfoId = buyMemberInfoId;
    }

    public Long getReceiveMemberInfoId() {
        return receiveMemberInfoId;
    }

    public void setReceiveMemberInfoId(Long receiveMemberInfoId) {
        this.receiveMemberInfoId = receiveMemberInfoId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return "B" + System.currentTimeMillis();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.BARRAGE.name();
    }

    @Override
    public String getBillSubject() {
        return "收费弹幕支付";
    }

    @Override
    public String getBillBody() {
        return "收费弹幕支付";
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getCross() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @JsonIgnore
    public Map<String, String> getPayParams() {
        // TODO Auto-generated method stub
        return null;
    }

}

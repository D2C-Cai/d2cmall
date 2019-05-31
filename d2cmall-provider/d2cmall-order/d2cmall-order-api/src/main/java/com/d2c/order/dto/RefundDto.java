package com.d2c.order.dto;

import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.model.Refund;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.model.base.IPaymentInterface;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class RefundDto extends Refund implements IPaymentInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 调拨单
     */
    private RequisitionItem requisitionItem;

    public RequisitionItem getRequisitionItem() {
        return requisitionItem;
    }

    public void setRequisitionItem(RequisitionItem requisitionItem) {
        this.requisitionItem = requisitionItem;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return this.getRefundSn();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.REFUND.name();
    }

    @Override
    public String getBillSubject() {
        return "订单编号:" + this.getOrderSn() + "," + "退款编号:" + getRefundSn();
    }

    @Override
    public String getBillBody() {
        // TODO Auto-generated method stub
        return null;
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
        return this.getOrderPaymentId();
    }

    @Override
    public Long getToMemberId() {
        // TODO Auto-generated method stub
        return null;
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
    public Map<String, String> getPayParams() {
        // TODO Auto-generated method stub
        return null;
    }

}

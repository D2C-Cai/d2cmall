package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Refund;
import com.d2c.order.model.Reship;
import org.apache.commons.lang3.StringUtils;

public class ReshipDto extends Reship {

    private static final long serialVersionUID = 1L;
    /**
     * 退款单
     */
    private Refund refund;

    @Override
    public String getReshipStatusName() {
        if (this.getReshipStatus() == null) {
            return "";
        }
        if (this.getReshipStatus() < 8) {
            ReshipStatus rr = ReshipStatus.getStatus(this.getReshipStatus());
            return rr.getName();
        } else {
            if (refund == null) {
                return "";
            }
            return this.refund.getRefundStatusName();
        }
    }

    public String getStatusName() {
        if (this.getReshipStatus() == null) {
            return "";
        }
        ReshipStatus rr = ReshipStatus.getStatus(this.getReshipStatus());
        return rr.getName();
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        Refund refund = this.getRefund();
        if (refund != null) {
            obj.put("backAccountName", refund.getBackAccountName());
            obj.put("backAccountSn", refund.getBackAccountSn());
            if (refund.getBackAccountType() != null) {
                obj.put("backAccountType", PaymentTypeEnum.getByCode(this.getRefund().getBackAccountType()));
            }
            obj.put("totalAmount", refund.getRealAmount());
        } else {
            obj.put("totalAmount", this.getTradeAmount());
        }
        return obj;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

}

package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.model.Exchange;
import com.d2c.order.model.Refund;
import org.apache.commons.lang3.StringUtils;

public class ExchangeDto extends Exchange {

    private static final long serialVersionUID = 1L;
    /**
     * 退款单
     */
    private Refund refund;

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public JSONObject getSpColor() {
        if (this.getSp1() != null) {
            JSONObject json = JSONObject.parseObject(this.getSp1());
            return json;
        }
        return null;
    }

    public JSONObject getSpSize() {
        if (this.getSp2() != null) {
            JSONObject json = JSONObject.parseObject(this.getSp2());
            return json;
        }
        return null;
    }

    public JSONObject getOldSPColor() {
        if (this.getOldSp1() != null) {
            JSONObject json = JSONObject.parseObject(this.getOldSp1());
            return json;
        }
        return null;
    }

    public JSONObject getOldSPSize() {
        if (this.getOldSp2() != null) {
            JSONObject json = JSONObject.parseObject(this.getOldSp2());
            return json;
        }
        return null;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

}

package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.base.IAddressInterface;
import com.d2c.order.model.base.IMemberInterface;
import com.d2c.order.model.base.IPaymentInterface;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CollageOrderDto extends CollageOrder implements IPaymentInterface, IAddressInterface, IMemberInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 团信息
     */
    private CollageGroup collageGroup;
    /**
     * 结束时间
     */
    private Date endTime;

    public CollageGroup getCollageGroup() {
        return collageGroup;
    }

    public void setCollageGroup(CollageGroup collageGroup) {
        this.collageGroup = collageGroup;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getMemberEmail() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMemberMobile() {
        // TODO Auto-generated method stub
        return null;
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
        return OrderTypeEnum.COLLAGE.name();
    }

    @Override
    public String getBillSubject() {
        return this.getProductName();
    }

    @Override
    public String getBillBody() {
        return this.getProductName();
    }

    @Override
    public BigDecimal getBillTotalFee() {
        return this.getPaidAmount();
    }

    @Override
    public BigDecimal getBillShipFee() {
        return this.getShippingRates();
    }

    @Override
    public Long getToMemberId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWaitPay() {
        return this.getStatus() != null && this.getStatus() == CollageOrder.CollageOrderStatus.WAITFORPAY.getCode();
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
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET;
        map.put("billPayTypes_", payType);
        return map;
    }

    public JSONObject toDtoJson() {
        JSONObject obj = super.toJson();
        JSONObject json = new JSONObject();
        if (this.getCollageGroup() != null) {
            json = this.getCollageGroup().toJson();
        }
        if (this.getEndTime() != null) {
            obj.put("endPayTime", this.getEndTime().getTime());
        }
        obj.put("collageGroup", json);
        return obj;
    }

}

package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 物流信息
 */
@Table(name = "o_logistics")
public class Logistics extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 物流单号
     */
    @AssertColumn("物流单号不能为空")
    private String deliverySn;
    /**
     * 物流信息
     */
    private String deliveryInfo;
    /**
     * 物流公司编号
     */
    private String deliveryCode;
    /**
     * 业务类型
     */
    private String type;
    /**
     * 状态 (0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单)
     */
    private Integer status = 0;
    /**
     * 物流公司名字
     */
    @Transient
    private String deliveryCorpName;

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeliveryCorpName() {
        return deliveryCorpName;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public String getStatusName() {
        if (status != null)
            switch (status) {
                case 1:
                    return "已揽件";
                case 2:
                    return "疑难";
                case 3:
                    return "已签收";
                case 4:
                    return "退签";
                case 5:
                    return "同城派送中";
                case 6:
                    return "退回";
                case 7:
                    return "转单";
                default:
                    return "在途中";
            }
        return "在途中";
    }

    public void setStatusName() {
    }

    public void createLogistics(String deliverySn, String com, String type, String creator) {
        this.setDeliverySn(deliverySn);
        this.setType(type);
        this.setDeliveryCode(com);
        this.setCreator(creator);
        this.setLastModifyMan(creator);
    }

    /**
     * ORDER 订单，RESHIP 退款退货，EXCHANGE 换货(客户申请换货)，ALLOT 调拨， DELIVERY 换货(公司给客户发货)
     */
    public enum BusinessType {
        ORDER, RESHIP, EXCHANGE, ALLOT, DELIVERY
    }

}

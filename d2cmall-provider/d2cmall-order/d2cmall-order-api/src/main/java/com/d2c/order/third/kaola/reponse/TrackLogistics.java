package com.d2c.order.third.kaola.reponse;

import java.util.List;

/**
 * 物流信息
 *
 * @author Lain
 */
public class TrackLogistics {

    /**
     * 物流状态
     */
    private Integer state;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 运货单号
     */
    private String billno;
    /**
     * 物流公司ID
     */
    private Integer logisticCompanyId;
    /**
     * 物流明细集合
     */
    private List<Track> tracks;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Integer getLogisticCompanyId() {
        return logisticCompanyId;
    }

    public void setLogisticCompanyId(Integer logisticCompanyId) {
        this.logisticCompanyId = logisticCompanyId;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

}

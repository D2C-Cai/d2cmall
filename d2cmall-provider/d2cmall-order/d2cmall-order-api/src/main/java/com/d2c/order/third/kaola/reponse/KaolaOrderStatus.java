package com.d2c.order.third.kaola.reponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class KaolaOrderStatus {

    /**
     * 订单ID
     */
    private String gorderId;
    /**
     * 明细信息
     */
    private List<KaolaOrderItemStatus> result;
    /**
     * 订单支付的总金额
     */
    private BigDecimal gpayAmount;
    /**
     * 忽略
     */
    private BigDecimal totalOverseaLogisticsAmount;
    /**
     * 忽略
     */
    private BigDecimal totalServiceFee;
    /**
     * 运费税
     */
    private BigDecimal logisticsTaxAmount;
    /**
     * 物流信息
     */
    private Map<String, List<TrackLogistics>> trackLogisticss;
    /**
     * 运费
     */
    private BigDecimal totalChinaLogisticsAmount;
    /**
     * 总税费
     */
    private BigDecimal totalTaxAmount;
    /**
     * 大订单状态(忽略)
     */
    private Integer gorderStatus;

    public String getGorderId() {
        return gorderId;
    }

    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    public List<KaolaOrderItemStatus> getResult() {
        return result;
    }

    public void setResult(List<KaolaOrderItemStatus> result) {
        this.result = result;
    }

    public BigDecimal getGpayAmount() {
        return gpayAmount;
    }

    public void setGpayAmount(BigDecimal gpayAmount) {
        this.gpayAmount = gpayAmount;
    }

    public BigDecimal getTotalOverseaLogisticsAmount() {
        return totalOverseaLogisticsAmount;
    }

    public void setTotalOverseaLogisticsAmount(BigDecimal totalOverseaLogisticsAmount) {
        this.totalOverseaLogisticsAmount = totalOverseaLogisticsAmount;
    }

    public BigDecimal getTotalServiceFee() {
        return totalServiceFee;
    }

    public void setTotalServiceFee(BigDecimal totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    public BigDecimal getLogisticsTaxAmount() {
        return logisticsTaxAmount;
    }

    public void setLogisticsTaxAmount(BigDecimal logisticsTaxAmount) {
        this.logisticsTaxAmount = logisticsTaxAmount;
    }

    public Map<String, List<TrackLogistics>> getTrackLogisticss() {
        return trackLogisticss;
    }

    public void setTrackLogisticss(Map<String, List<TrackLogistics>> trackLogisticss) {
        this.trackLogisticss = trackLogisticss;
    }

    public BigDecimal getTotalChinaLogisticsAmount() {
        return totalChinaLogisticsAmount;
    }

    public void setTotalChinaLogisticsAmount(BigDecimal totalChinaLogisticsAmount) {
        this.totalChinaLogisticsAmount = totalChinaLogisticsAmount;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public Integer getGorderStatus() {
        return gorderStatus;
    }

    public void setGorderStatus(Integer gorderStatus) {
        this.gorderStatus = gorderStatus;
    }

}
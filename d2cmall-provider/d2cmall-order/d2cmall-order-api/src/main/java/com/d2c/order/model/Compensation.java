package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.model.RequisitionItem.ItemType;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计师赔偿单
 */
@Table(name = "o_compensation")
public class Compensation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private String orderSn;
    /**
     * 订单明细ID
     */
    private Long orderItemId;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 调拨单明细ID
     */
    private Long requisitionItemId;
    /**
     * 调拨单类型
     */
    private Integer requisitionType;
    /**
     * 答应用户的预计发货时间
     */
    private Date estimateDate;
    /**
     * 状态 0创建 8处理成功
     */
    private Integer status = 0;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 调拨金额
     */
    private BigDecimal tradeAmount;
    /**
     * 赔偿金额
     */
    private BigDecimal amount;
    /**
     * 实际赔付金额
     */
    private BigDecimal actualAmount;
    /**
     * 结算操作人
     */
    private String balanceMan;
    /**
     * 结算时间
     */
    private Date balanceDate;
    /**
     * 财务备注
     */
    private String remark;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 所关联的对账单
     */
    private Long statementId;
    /**
     * 类型 designer:设计师赔偿,store:门店赔偿
     */
    private String type;

    public Compensation() {
    }

    public Compensation(RequisitionItem item) {
        this.orderSn = item.getOrderSn();
        this.orderItemId = item.getOrderItemId();
        this.designerId = item.getDesignerId();
        this.brandName = item.getDesignerName();
        this.requisitionSn = item.getRequisitionSn();
        this.requisitionItemId = item.getId();
        this.requisitionType = item.getType();
        this.estimateDate = item.getExpiredDate();
        this.status = 0;
        this.operation = item.getOperation();
        if (item.getType() == ItemType.STOREALLC.getCode()) {
            this.type = "store";
            this.storeId = item.getStoreId();
            this.storeName = item.getStoreName();
        } else {
            this.type = "designer";
        }
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRequisitionItemId() {
        return requisitionItemId;
    }

    public void setRequisitionItemId(Long requisitionItemId) {
        this.requisitionItemId = requisitionItemId;
    }

    public Integer getRequisitionType() {
        return requisitionType;
    }

    public void setRequisitionType(Integer requisitionType) {
        this.requisitionType = requisitionType;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getBalanceMan() {
        return balanceMan;
    }

    public void setBalanceMan(String balanceMan) {
        this.balanceMan = balanceMan;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    /**
     * 赔偿方式：
     * <li>normal 按天数，比例算
     * <li>full min(30%,300)
     */
    public enum CompensateMethod {
        NORMAL, FULL;
    }

}

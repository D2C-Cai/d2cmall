package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;

import java.util.Date;
import java.util.List;

public class RequisitionItemSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 调拨单ID
     */
    private Long id;
    /**
     * 状态
     */
    private Integer[] status;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型
     */
    private Integer[] types;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 官网货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师ID
     */
    private List<Long> designerIds;
    /**
     * 调拨来源
     */
    private Long storeId;
    /**
     * 订单明细ID
     */
    private Long orderItemId;
    /**
     * 订单明细状态
     */
    private String orderItemStatus;
    /**
     * 罚款标志
     */
    private Integer fine;
    /**
     * 是否处理
     */
    private Integer handle;
    /**
     * 采购类型 NORMAL,BIGDEMAND,CUSTOMER
     */
    private String purchaseReason;
    /**
     * 调拨原因 NORMAL,INFERIOR,UNSALE
     */
    private String allocateReason;
    /**
     * 采购开始时间
     */
    private Date createDateStart;
    /**
     * 采购结束时间
     */
    private Date createDateEnd;
    /**
     * 入库时间开始
     */
    private Date receiveDateStart;
    /**
     * 入库时间结束
     */
    private Date receiveDateEnd;
    /**
     * 调拨时间开始
     */
    private Date requisitionDateStart;
    /**
     * 调拨时间结束
     */
    private Date requisitionDateEnd;
    /**
     * 预计发货时间开始
     */
    private Date estimateDateStart;
    /**
     * 预计发货时间结束
     */
    private Date estimateDateEnd;
    /**
     * 设计师预计发货时间开始
     */
    private Date designerEstimateDateStart;
    /**
     * 设计师预计发货时间结束
     */
    private Date designerEstimateDateEnd;
    /**
     * 物流公司名称
     */
    private String deliveryCorp;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 拦截状态
     */
    private Integer locked;
    /**
     * 关联sn
     */
    private String relationSn;
    /**
     * 响应速度
     */
    private Integer responseSpeed;
    /**
     * 发货开始时间
     */
    private Date deliveryStartDate;
    /**
     * 发货结束时间
     */
    private Date deliveryEndDate;
    /**
     * 物流是否签收
     */
    private Integer received;
    /**
     * 官网条码
     */
    private String barCode;
    /**
     * 门店or设计师 (store,designer,admin)
     */
    private String roleType;
    /**
     * 门店采购申请人
     */
    private String applyMan;
    /**
     * 品牌直发
     */
    private Integer direct;
    /**
     * 最晚发货时间
     */
    private Date beginExpiredDate;
    private Date endExpiredDate;
    /**
     * 是否已发货
     */
    private Integer delivered;

    public Integer getResponseSpeed() {
        return responseSpeed;
    }

    public void setResponseSpeed(Integer responseSpeed) {
        this.responseSpeed = responseSpeed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer[] getTypes() {
        return types;
    }

    public void setTypes(Integer[] types) {
        this.types = types;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    public Date getEstimateDateStart() {
        return estimateDateStart;
    }

    public void setEstimateDateStart(Date estimateDateStart) {
        this.estimateDateStart = estimateDateStart;
    }

    public Date getEstimateDateEnd() {
        return estimateDateEnd;
    }

    public void setEstimateDateEnd(Date estimateDateEnd) {
        this.estimateDateEnd = estimateDateEnd;
    }

    public Date getDesignerEstimateDateStart() {
        return designerEstimateDateStart;
    }

    public void setDesignerEstimateDateStart(Date designerEstimateDateStart) {
        this.designerEstimateDateStart = designerEstimateDateStart;
    }

    public Date getDesignerEstimateDateEnd() {
        return designerEstimateDateEnd;
    }

    public void setDesignerEstimateDateEnd(Date designerEstimateDateEnd) {
        this.designerEstimateDateEnd = designerEstimateDateEnd;
    }

    public Date getRequisitionDateStart() {
        return requisitionDateStart;
    }

    public void setRequisitionDateStart(Date requisitionDateStart) {
        this.requisitionDateStart = requisitionDateStart;
    }

    public Date getRequisitionDateEnd() {
        return requisitionDateEnd;
    }

    public void setRequisitionDateEnd(Date requisitionDateEnd) {
        this.requisitionDateEnd = requisitionDateEnd;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(String orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getReceiveDateStart() {
        return receiveDateStart;
    }

    public void setReceiveDateStart(Date receiveDateStart) {
        this.receiveDateStart = receiveDateStart;
    }

    public Date getReceiveDateEnd() {
        return receiveDateEnd;
    }

    public void setReceiveDateEnd(Date receiveDateEnd) {
        this.receiveDateEnd = receiveDateEnd;
    }

    public String getDeliveryCorp() {
        return deliveryCorp;
    }

    public void setDeliveryCorp(String deliveryCorp) {
        this.deliveryCorp = deliveryCorp;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getRelationSn() {
        return relationSn;
    }

    public void setRelationSn(String relationSn) {
        this.relationSn = relationSn;
    }

    public Date getDeliveryStartDate() {
        return deliveryStartDate;
    }

    public void setDeliveryStartDate(Date deliveryStartDate) {
        this.deliveryStartDate = deliveryStartDate;
    }

    public Date getDeliveryEndDate() {
        return deliveryEndDate;
    }

    public void setDeliveryEndDate(Date deliveryEndDate) {
        this.deliveryEndDate = deliveryEndDate;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    public String getAllocateReason() {
        return allocateReason;
    }

    public void setAllocateReason(String allocateReason) {
        this.allocateReason = allocateReason;
    }

    public String getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designerIds = brandIds;
    }

    public Date getBeginExpiredDate() {
        return beginExpiredDate;
    }

    public void setBeginExpiredDate(Date beginExpiredDate) {
        this.beginExpiredDate = beginExpiredDate;
    }

    public Date getEndExpiredDate() {
        return endExpiredDate;
    }

    public void setEndExpiredDate(Date endExpiredDate) {
        this.endExpiredDate = endExpiredDate;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

}

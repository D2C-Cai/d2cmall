package com.d2c.main.openapi.view;

import com.d2c.common.api.view.BaseVO;
import com.d2c.order.model.RequisitionItem;

import java.util.Date;

/**
 * 调拨单
 */
public class RequisitionItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    /**
     * 调拨单ID
     */
    private Long requisitionId;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 调拨时间
     */
    private Date requisitionDate;
    /**
     * 类型
     *
     * @see 采购：1.订单采购 2.门店采购 4.货需采购
     * @see 调拨：3.门店调拨 7.仓库调拨
     * @see 退货：5.POP退货 6.自营退货
     */
    private String typeName;
    /**
     * 调拨数量
     */
    private Integer quantity;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 优先级 0：一般 1：紧急 2:非常紧急
     */
    private Integer priority;
    /**
     * 设计师条码
     */
    private String externalCode;
    /**
     * 官网货号
     */
    private String productSn;
    /**
     * 官网条码
     */
    private String barCode;
    /**
     * 颜色
     */
    private String sp1;
    /**
     * 尺码
     */
    private String sp2;
    /**
     * 答应用户的预计发货时间
     */
    private Date estimateDate;
    /**
     * 设计师备注
     */
    private String designerMemo;
    /**
     * 收货人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * View 视图转换
     */
    public RequisitionItemVO convert(RequisitionItem bean) {
        this.setRequisitionId(bean.getId());
        this.setSp1(bean.getSp1Value());
        this.setSp2(bean.getSp2Value());
        this.setTypeName(bean.getTypeName());
        this.setTypeName(bean.getTypeName());
        this.setStatusName(bean.getStatusName());
        return this;
    }
    // ******************************************

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDesignerMemo() {
        return designerMemo;
    }

    public void setDesignerMemo(String designerMemo) {
        this.designerMemo = designerMemo;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    // public String getStatus() {
    // return statusName;
    // }
    //
    // public String getType() {
    // return typeName;
    // }
}

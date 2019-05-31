package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 分销返利单
 */
@Table(name = "o_partner_bill")
public class PartnerBill extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 返利单号
     */
    private String sn;
    /**
     * 1交易中 8交易完成 -1已关闭
     */
    private Integer status;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 明细订单ID
     */
    private Long orderItemId;
    /**
     * 购买商品ID
     */
    private Long productId;
    /**
     * 购买商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productPic;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 实际明细订单支付金额
     */
    private BigDecimal actualAmount;
    /**
     * 顶级返利ID
     */
    private Long masterId;
    /**
     * 间接返利ID
     */
    private Long superId;
    /**
     * 间接返利ID
     */
    private Long parentId;
    /**
     * 直接返利ID
     */
    private Long partnerId;
    /**
     * 顶级返利比
     */
    private BigDecimal masterRatio;
    /**
     * 间接返利比
     */
    private BigDecimal superRatio;
    /**
     * 间接返利比
     */
    private BigDecimal parentRatio;
    /**
     * 直接返利比
     */
    private BigDecimal partnerRatio;
    /**
     * 分销账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String partnerCode;
    /**
     * 分销等级
     */
    private Integer partnerLevel;
    /**
     * 购买会员账号
     */
    private String buyerCode;
    /**
     * 间接返利账号
     */
    private String topPartnerCode;
    /**
     * 是否异常单据
     */
    private Integer excep = 0;

    public PartnerBill() {
        this.status = 1;
        this.sn = SerialNumUtil.buildPatrnerBillSn();
    }

    public PartnerBill(OrderItem oi) {
        this.status = 1;
        this.sn = SerialNumUtil.buildPatrnerBillSn();
        this.orderItemId = oi.getId();
        this.productId = oi.getProductId();
        this.productName = oi.getProductName();
        this.productPic = oi.getProductImg();
        this.productSn = oi.getProductSn();
        this.actualAmount = oi.getActualAmount().subtract(oi.getTaxAmount());
        this.orderSn = oi.getOrderSn();
        this.masterId = oi.getMasterId();
        this.superId = oi.getSuperId();
        this.parentId = oi.getParentId();
        this.partnerId = oi.getPartnerId();
        this.masterRatio = oi.getMasterRatio();
        this.superRatio = oi.getSuperRatio();
        this.parentRatio = oi.getParentRatio();
        this.partnerRatio = oi.getPartnerRatio();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getMasterRatio() {
        return masterRatio;
    }

    public void setMasterRatio(BigDecimal masterRatio) {
        this.masterRatio = masterRatio;
    }

    public BigDecimal getSuperRatio() {
        return superRatio;
    }

    public void setSuperRatio(BigDecimal superRatio) {
        this.superRatio = superRatio;
    }

    public BigDecimal getParentRatio() {
        return parentRatio;
    }

    public void setParentRatio(BigDecimal parentRatio) {
        this.parentRatio = parentRatio;
    }

    public BigDecimal getPartnerRatio() {
        return partnerRatio;
    }

    public void setPartnerRatio(BigDecimal partnerRatio) {
        this.partnerRatio = partnerRatio;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getPartnerLevel() {
        return partnerLevel;
    }

    public void setPartnerLevel(Integer partnerLevel) {
        this.partnerLevel = partnerLevel;
    }

    public Integer getExcep() {
        return excep;
    }

    public void setExcep(Integer excep) {
        this.excep = excep;
    }

    public String getTopPartnerCode() {
        return topPartnerCode;
    }

    public void setTopPartnerCode(String topPartnerCode) {
        this.topPartnerCode = topPartnerCode;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getStatusName() {
        switch (this.getStatus()) {
            case -1:
                return "已关闭";
            case 1:
                return "交易中";
            case 8:
                return "已完成";
        }
        return "状态异常";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("sn", this.getSn());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("actualAmount", this.getActualAmount());
        obj.put("status", this.getStatus());
        obj.put("statusName", this.getStatusName());
        obj.put("productPic", this.getProductPic());
        obj.put("productName", this.getProductName());
        obj.put("buyerCode", StringUt.hideMobile(this.getBuyerCode()));
        return obj;
    }

}

package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 伯俊接口错误日志
 */
@Table(name = "log_burgeon_error")
public class BurgeonErrorLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 单据ID
     */
    private Long sourceId;
    /**
     * 单据日期
     */
    private Date billDate;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 伯俊单号
     */
    private String burgeonSn;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单据类型
     */
    private String type;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 是否处理
     */
    private Integer handle = 0;
    /**
     * 处理人
     */
    private String handleMan;
    /**
     * 处理时间
     */
    private Date handleDate;
    /**
     * 处理内容
     */
    private String handleContent;
    /**
     * 失败单据数
     */
    private Integer fallCount = 0;
    /**
     * 单据来源类型
     */
    private String sourceType = DocSourceType.REQUISITION.name();

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getBurgeonSn() {
        return burgeonSn;
    }

    public void setBurgeonSn(String burgeonSn) {
        this.burgeonSn = burgeonSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public String getHandleMan() {
        return handleMan;
    }

    public void setHandleMan(String handleMan) {
        this.handleMan = handleMan;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public Integer getFallCount() {
        return fallCount;
    }

    public void setFallCount(Integer fallCount) {
        this.fallCount = fallCount;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getLog() {
        if (this.getHandle() == 0) {
            return "";
        }
        return "操作人:" + this.getHandleMan() + ",操作内容:" + this.getHandleContent();
    }

    public enum DocSourceType {
        REQUISITION, ORDERITEM
    }

    public enum DocType {
        Retail("零售单"), Retretail("零售退货单"), Pur("采购单"), Retpur("采购退货单"), Transfer("调拨单");

        private String display;

        DocType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}

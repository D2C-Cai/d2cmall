package com.d2c.report.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单商品日统计分析
 */
@Table(name = "rp_product_order_analysis")
public class ProductOrderAnalysis extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 付款时间（2016-11-11 00:00:00）
     */
    private Date orderDate;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 货号
     */
    private String productSn;
    /**
     * 条码
     */
    private String productSku;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 销售数量
     */
    private int saleQuantity;
    /**
     * 发货数量
     */
    private int deliverQuantity;
    /**
     * 退货数量（afterClose的数量）
     */
    private int closeQuantity;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 商品图片
     */
    private String productImg;

    public ProductOrderAnalysis() {
    }

    public ProductOrderAnalysis(Map<String, Object> map, Date orderDate, BuzType type) {
        this.orderDate = orderDate;
        this.productId = (Long) map.get("productId");
        this.productSn = (String) map.get("productSn");
        this.productSku = (String) map.get("productSku");
        this.designerId = (Long) map.get("designerId");
        this.designerCode = (String) map.get("designerCode");
        this.designerName = (String) map.get("designerName");
        this.saleQuantity = type.equals(BuzType.SALE) ? ((BigDecimal) map.get("quantity")).intValue() : 0;
        this.deliverQuantity = type.equals(BuzType.DELIVERY) ? ((BigDecimal) map.get("quantity")).intValue() : 0;
        this.closeQuantity = type.equals(BuzType.CLOSE) ? ((BigDecimal) map.get("quantity")).intValue() : 0;
        this.operation = (String) map.get("operation");
        this.productName = (String) map.get("productName");
        this.productImg = (String) map.get("productImg");
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public int getDeliverQuantity() {
        return deliverQuantity;
    }

    public void setDeliverQuantity(int deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCloseQuantity() {
        return closeQuantity;
    }

    public void setCloseQuantity(int closeQuantity) {
        this.closeQuantity = closeQuantity;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public enum BuzType {
        SALE, DELIVERY, CLOSE
    }

}

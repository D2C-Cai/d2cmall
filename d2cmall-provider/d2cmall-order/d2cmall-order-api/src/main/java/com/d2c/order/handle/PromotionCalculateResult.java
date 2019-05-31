package com.d2c.order.handle;

import com.d2c.order.model.Order.OrderType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PromotionCalculateResult implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单类型 普通/分销
     */
    private String type = OrderType.ordinary.name();
    /**
     * 参与活动的明细项
     */
    private List<PromotionCalculateItem> items;
    /**
     * 订单应付运费
     */
    private BigDecimal shipFee = new BigDecimal("0");
    /**
     * 商品优惠总金额
     */
    private BigDecimal productPromotionAmount = new BigDecimal("0");
    /**
     * 订单优惠总金额
     */
    private BigDecimal orderPromotionAmount = new BigDecimal("0");
    /**
     * 订单商品总金额
     */
    private BigDecimal productTotalAmount = new BigDecimal("0");
    /**
     * 订单商品总数量
     */
    private Integer productTotalQuantity = 0;
    /**
     * 订单优惠名称
     */
    private String orderPromotionNames = "";
    /**
     * 订单应付金额
     */
    private BigDecimal totalAmount = new BigDecimal("0");
    /**
     * 库存不足
     */
    private Boolean lowStock;

    public static BigDecimal shippingRates(BigDecimal needAmount, BigDecimal shipAmount, BigDecimal totalAmount) {
        BigDecimal X = needAmount;
        BigDecimal Y = shipAmount;
        if (X.compareTo(totalAmount) > 0 && totalAmount.compareTo(new BigDecimal("1.00")) > 0) {
            return Y;
        } else {
            return new BigDecimal(0);
        }
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public BigDecimal getProductPromotionAmount() {
        return productPromotionAmount;
    }

    public void setProductPromotionAmount(BigDecimal productPromotionAmount) {
        this.productPromotionAmount = productPromotionAmount;
    }

    public BigDecimal getOrderPromotionAmount() {
        return orderPromotionAmount;
    }

    public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
        this.orderPromotionAmount = orderPromotionAmount;
    }

    public BigDecimal getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(BigDecimal productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    public Integer getProductTotalQuantity() {
        return productTotalQuantity;
    }

    public void setProductTotalQuantity(Integer productTotalQuantity) {
        this.productTotalQuantity = productTotalQuantity;
    }

    public String getOrderPromotionNames() {
        return orderPromotionNames;
    }

    public void setOrderPromotionNames(String orderPromotionNames) {
        this.orderPromotionNames = orderPromotionNames;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getLowStock() {
        return lowStock;
    }

    public void setLowStock(Boolean lowStock) {
        this.lowStock = lowStock;
    }

    public List<PromotionCalculateItem> getItems() {
        if (items == null) {
            items = new ArrayList<PromotionCalculateItem>();
        }
        return items;
    }

    public void setItems(List<PromotionCalculateItem> items) {
        this.items = items;
    }

    public void addItem(PromotionCalculateItem item) {
        getItems().add(item);
        this.productPromotionAmount = productPromotionAmount.add(item.getPromotionAmount());
        this.productTotalAmount = productTotalAmount.add(item.getTotalProductAmount());
        this.productTotalQuantity = productTotalQuantity + item.getQuantity();
        this.totalAmount = productTotalAmount.subtract(productPromotionAmount);
    }

}

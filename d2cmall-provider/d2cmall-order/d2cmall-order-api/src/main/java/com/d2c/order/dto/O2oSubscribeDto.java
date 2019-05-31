package com.d2c.order.dto;

import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.model.O2oSubscribeItem;

import java.math.BigDecimal;
import java.util.List;

public class O2oSubscribeDto extends O2oSubscribe {

    private static final long serialVersionUID = 1L;
    /**
     * 预约次数
     */
    private Integer times;
    /**
     * 明细商品件数
     */
    private Integer totalQuantity;
    /**
     * 明细商品总金额
     */
    private BigDecimal totalAmount;
    /**
     * 预约单明细
     */
    private List<O2oSubscribeItem> items;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<O2oSubscribeItem> getItems() {
        return items;
    }

    public void setItems(List<O2oSubscribeItem> items) {
        int totalQuantity = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        for (O2oSubscribeItem item : items) {
            if (item.getQuantity() == null) {
                item.setQuantity(1);
            }
            totalQuantity += item.getQuantity();
            totalAmount = totalAmount.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        this.setTotalQuantity(totalQuantity);
        this.setTotalAmount(totalAmount);
        this.items = items;
    }

}

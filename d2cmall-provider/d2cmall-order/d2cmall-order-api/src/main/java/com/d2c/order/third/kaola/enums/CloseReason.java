package com.d2c.order.third.kaola.enums;

public enum CloseReason {
    ReciverError(1, "收货人信息有误"), OrderError(2, "商品数量或款式需调整"), BestPlan(3, "有更优惠的购买方案"),
    NotDelivery(4, "考拉一直未发货"), Stock(5, "商品缺货"), No(6, "我不想买了"), Other(7, "其他原因");

    private Integer id;
    private String reason;
    CloseReason(Integer id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单日志
 */
@Table(name = "log_order")
public class OrderLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private int logType;
    /**
     * 日志信息
     */
    private String info;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单明细ID
     */
    private Long orderItemId;

    public OrderLogType getOrderLogType() {
        return OrderLogType.getStatus(logType);
    }

    ;

    public void setOrderLogType(OrderLogType orderLogType) {
        this.logType = orderLogType.getCode();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    /**
     * -3 删除 -1 关闭 0 未处理 1 待付款 2 已付款待审核 3 已审核待发货 4 已发货 5 门店发货 6 售后 8 交易成功 9开票 16
     * 更新 50 预计发货
     */
    public enum OrderLogType {
        Delete(-3), Close(-1), Initial(0), WaitingForPay(1),
        WaitingForConfirmation(2), WaitingForDelivery(3), Delivered(4), DeliveryToStock(5),
        AfterSale(6), Success(8), Invoiced(9), Update(16), ItemEstimateDateLog(50);
        private static Map<Integer, OrderLogType> holder = new HashMap<Integer, OrderLogType>();

        static {
            for (OrderLogType logType : values()) {
                holder.put(logType.getCode(), logType);
            }
        }

        private int code;

        OrderLogType(int code) {
            this.code = code;
        }

        public static OrderLogType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

}
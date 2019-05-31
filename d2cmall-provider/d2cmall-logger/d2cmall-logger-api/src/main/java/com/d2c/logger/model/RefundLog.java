package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款单日志
 */
@Table(name = "log_refund")
public class RefundLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 日志类型
     */
    private int logType;
    /**
     * 日志信息
     */
    private String info;
    /**
     * 退款单ID
     */
    private Long refundId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单明细ID
     */
    private Long orderItemId;

    public RefundLog() {
    }

    public RefundLog(Long refundId, String info, RefundLogType type) {
        this.setRefundId(refundId);
        this.setInfo(info);
        this.setRefundLogType(type);
        this.setCreateDate(new Date());
    }

    public RefundLogType getRefundLogType() {
        return RefundLogType.getStatus(logType);
    }

    ;

    public void setRefundLogType(RefundLogType refundLogType) {
        this.logType = refundLogType.getCode();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
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

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("creator", this.getCreator());
        obj.put("createDate", this.getCreateDate());
        obj.put("info", this.getInfo());
        obj.put("info", this.getInfo());
        obj.put("logType", this.getLogType());
        return obj;
    }

    /**
     * 0 创建 1 确认 2 拒绝 3 完成 -1 用户取消 -2 平台关闭 4 锁定 4 已发货
     */
    public enum RefundLogType {
        create(0), modify(1), refuse(2), completed(3), memberClose(-1), mallClose(-2), lock(4);
        private static Map<Integer, RefundLogType> holder = new HashMap<Integer, RefundLogType>();

        static {
            holder.put(0, RefundLogType.create);
            holder.put(1, RefundLogType.modify);
            holder.put(2, RefundLogType.refuse);
            holder.put(3, RefundLogType.completed);
            holder.put(-1, RefundLogType.memberClose);
            holder.put(-2, RefundLogType.mallClose);
        }

        private int code;

        RefundLogType(int code) {
            this.code = code;
        }

        public static RefundLogType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

}

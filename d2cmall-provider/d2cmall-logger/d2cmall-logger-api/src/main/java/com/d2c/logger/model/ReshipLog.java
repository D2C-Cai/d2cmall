package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 退货单日志
 */
@Table(name = "log_reship")
public class ReshipLog extends PreUserDO {

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
     * 退货单ID
     */
    private Long reshipId;
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

    public ReshipLogType getReshipLogType() {
        return ReshipLogType.getStatus(logType);
    }

    ;

    public void setReshipLogType(ReshipLogType reshipLogType) {
        this.logType = reshipLogType.getCode();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getReshipId() {
        return reshipId;
    }

    public void setReshipId(Long reshipId) {
        this.reshipId = reshipId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
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
     * -1 关闭 0 创建中 1 确认 2 拒绝 3 完成 4 申请 5 发货 6 收货 7物流显示签收，待仓库确认收货
     */
    public enum ReshipLogType {
        close(-1), create(0), modify(1), refuse(2), completed(3), apply(4), sended(5), receive(6), sign(7);
        private static Map<Integer, ReshipLogType> holder = new HashMap<Integer, ReshipLogType>();

        static {
            holder.put(-1, ReshipLogType.close);
            holder.put(0, ReshipLogType.create);
            holder.put(1, ReshipLogType.modify);
            holder.put(2, ReshipLogType.refuse);
            holder.put(3, ReshipLogType.completed);
            holder.put(4, ReshipLogType.apply);
            holder.put(5, ReshipLogType.sended);
            holder.put(6, ReshipLogType.receive);
            holder.put(7, ReshipLogType.sign);
        }

        private int code;

        ReshipLogType(int code) {
            this.code = code;
        }

        public static ReshipLogType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

}
package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 换货单日志
 */
@Table(name = "log_exchange")
public class ExchangeLog extends PreUserDO {

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
    private Long exchangeId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单明细ID
     */
    private Long orderItemId;

    public ExchangeLogType getExchangeLogType() {
        return ExchangeLogType.getStatus(logType);
    }

    ;

    public void setExchangeLogType(ExchangeLogType exchangeLogType) {
        this.logType = exchangeLogType.getCode();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
     * -1 关闭 0 创建中 1 确认 2 拒绝 3 完成 4 申请 5 发货 6 收货7物流显示仓库签收,待仓库确认
     */
    public enum ExchangeLogType {
        close(-1), create(0), modify(1), refuse(2), completed(3), apply(4), sended(5), receive(6), sign(7);
        private static Map<Integer, ExchangeLogType> holder = new HashMap<Integer, ExchangeLogType>();

        static {
            holder.put(-1, ExchangeLogType.close);
            holder.put(0, ExchangeLogType.create);
            holder.put(1, ExchangeLogType.modify);
            holder.put(2, ExchangeLogType.refuse);
            holder.put(3, ExchangeLogType.completed);
            holder.put(4, ExchangeLogType.apply);
            holder.put(5, ExchangeLogType.sended);
            holder.put(6, ExchangeLogType.receive);
            holder.put(7, ExchangeLogType.sign);
        }

        private int code;

        ExchangeLogType(int code) {
            this.code = code;
        }

        public static ExchangeLogType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

}
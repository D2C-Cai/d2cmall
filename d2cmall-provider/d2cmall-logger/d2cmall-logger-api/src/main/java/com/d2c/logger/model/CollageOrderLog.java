package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 拼团单日志
 *
 * @author wwn
 */
@Table(name = "log_collage_order")
public class CollageOrderLog extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 拼团单id
     */
    private Long orderId;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 信息
     */
    private String info;

    public CollageOrderLog() {
    }

    public CollageOrderLog(Long orderId, String logType, String info, String operator) {
        this.orderId = orderId;
        this.logType = logType;
        this.info = info;
        this.creator = operator;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public enum CollageLogType {
        CREATE, SUCCESS, WAITFORREFUND, CLOSE, REFUND, PAY
    }

}

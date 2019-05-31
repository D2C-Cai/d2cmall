package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 调拨单日志
 */
@Table(name = "log_requisition")
public class RequisitionLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String logType;
    /**
     * 日志信息
     */
    private String info;
    /**
     * 调拨单ID
     */
    private Long requisitionId;
    /**
     * 调拨单明细ID
     */
    private Long requisitionItemId;
    /**
     * 调拨单号
     */
    private String requisitionSn;

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Long getRequisitionItemId() {
        return requisitionItemId;
    }

    public void setRequisitionItemId(Long requisitionItemId) {
        this.requisitionItemId = requisitionItemId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

}

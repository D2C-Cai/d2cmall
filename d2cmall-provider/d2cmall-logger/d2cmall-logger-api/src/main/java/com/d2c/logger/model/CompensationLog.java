package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 赔偿单日志
 */
@Table(name = "log_compensation")
public class CompensationLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 设计师赔偿单ID
     */
    private Long compensationId;
    /**
     * 客户端赔偿单ID
     */
    private Long customerCompensationId;
    /**
     * 信息
     */
    private String info;

    public CompensationLog() {
        super();
    }

    public CompensationLog(Long compensationId, Long customerCompensationId, String info, String operator) {
        super();
        this.compensationId = compensationId;
        this.info = info;
        this.creator = operator;
        this.customerCompensationId = customerCompensationId;
    }

    public Long getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(Long compensationId) {
        this.compensationId = compensationId;
    }

    public Long getCustomerCompensationId() {
        return customerCompensationId;
    }

    public void setCustomerCompensationId(Long customerCompensationId) {
        this.customerCompensationId = customerCompensationId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

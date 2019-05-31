package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 分销日志
 */
@Table(name = "log_partner")
public class PartnerLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 业务类型
     */
    private String type;
    /**
     * 操作信息
     */
    private String info;
    /**
     * 分销Id
     */
    private Long partnerId;

    public PartnerLog() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

}

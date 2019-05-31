package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 设计师日志
 */
@Table(name = "log_designers")
public class DesignersLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 设计师ID
     */
    private Long designersId;
    /**
     * 操作信息
     */
    private String info;

    public Long getDesignersId() {
        return designersId;
    }

    public void setDesignersId(Long designersId) {
        this.designersId = designersId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

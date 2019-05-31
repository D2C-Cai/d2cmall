package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -IP黑名单
 */
@Table(name = "v_ip_black")
public class IpBlack extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 黑名单ip
     */
    @AssertColumn("IP不能为空")
    private String ip;
    /**
     * 1:加入黑名单 0：删除黑名单 -1：逻辑删除
     */
    private Integer status = 1;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

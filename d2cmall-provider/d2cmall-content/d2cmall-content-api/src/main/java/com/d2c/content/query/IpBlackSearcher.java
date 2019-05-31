package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

/**
 * IP黑名单
 */
public class IpBlackSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private String ip;
    private Integer status;

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

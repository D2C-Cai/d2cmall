package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 系统错误日志
 */
@Table(name = "errors")
public class ErrorLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 服务器IP
     */
    private String ip;
    /**
     * 错误信息
     */
    private String errorInfo;
    /**
     * 来源
     */
    private String source;

    @Override
    public String toString() {
        return ip + ", " + source + ", 错误信息: " + errorInfo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void createErrorLog(String ip, String source, String errorInfo) {
        this.ip = ip;
        this.source = source;
        this.errorInfo = errorInfo;
    }

}

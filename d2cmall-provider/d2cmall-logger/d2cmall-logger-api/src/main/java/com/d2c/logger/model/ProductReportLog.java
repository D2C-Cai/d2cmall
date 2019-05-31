package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品报告日志
 */
@Table(name = "log_product_report")
public class ProductReportLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 商品报告ID
     */
    private Long reportId;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 信息
     */
    private String info;

    public ProductReportLog() {
    }

    public ProductReportLog(Long reportId, String logType, String info, String operator) {
        this.reportId = reportId;
        this.logType = logType;
        this.info = info;
        this.creator = operator;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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

    public String getTypeName() {
        return ReportType.valueOf(this.getLogType()).name;
    }

    public enum ReportType {
        refuse("审核不通过"), submit("提交"), cancel("取消提交"), delete("删除"), verify("审核通过");
        private String name;

        private ReportType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

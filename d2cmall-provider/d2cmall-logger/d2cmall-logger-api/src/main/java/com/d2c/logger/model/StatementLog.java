package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.text.SimpleDateFormat;

/**
 * 对账单日志
 */
@Table(name = "log_statement")
public class StatementLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String logType;
    /**
     * 信息
     */
    private String info;
    /**
     * 对账单ID
     */
    private Long statementId;
    /**
     * 对账单明细ID
     */
    private Long statementItemId;

    public StatementLog() {
    }

    public StatementLog(String logType, String info, Long statementId, Long statementItemId, String operator) {
        this.logType = logType;
        this.info = info;
        this.statementId = statementId;
        this.statementItemId = statementItemId;
        this.creator = operator;
    }

    public String getStatementLogType() {
        switch (logType) {
            case "Delete":
                return "删除";
            case "Modify":
                return "修改";
            case "Send":
                return "发送";
            case "RETREAT":
                return "撤回";
            case "Receive":
                return "接收";
            case "Back":
                return "驳回";
            case "Confirm":
                return "确认";
            case "Pay":
                return "支付";
            case "Success":
                return "结算完成";
        }
        return "未知";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Long getStatementItemId() {
        return statementItemId;
    }

    public void setStatementItemId(Long statementItemId) {
        this.statementItemId = statementItemId;
    }

    public String getDateString() {
        if (this.createDate != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf.format(this.createDate);
        }
        return "";
    }

    public enum LogType {
        Delete, Modify, Send, Receive, Back, Confirm, Pay, Success, RETREAT
    }

}

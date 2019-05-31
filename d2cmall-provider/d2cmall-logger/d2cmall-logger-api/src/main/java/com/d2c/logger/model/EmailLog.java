package com.d2c.logger.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 邮件发送日志
 */
@Table(name = "log_email")
public class EmailLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @AssertColumn("邮件标题不能为空")
    private String subject;
    /**
     * 内容
     */
    @AssertColumn("邮件内容不能为空")
    private String content;
    /**
     * 来源ID
     */
    private Long sourceId;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 类型
     */
    private String type;
    /**
     * 发送成功次数
     */
    private int send = 0;
    /**
     * 最大发送次数
     */
    private int maxSend = 10;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getMaxSend() {
        return maxSend;
    }

    public void setMaxSend(int maxSend) {
        this.maxSend = maxSend;
    }

    public enum EmailLogType {
        MEMBER, REMIND
    }

}

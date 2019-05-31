package com.d2c.logger.support;

import com.d2c.logger.model.EmailLog.EmailLogType;

import java.io.Serializable;
import java.util.Map;

public class EmailBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 标题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 模板ID
     */
    private Long templateId;
    /**
     * 邮件类型
     */
    private EmailLogType emailLogType;
    /**
     * 替换文本
     */
    private Map<String, String> emailC;
    /**
     * 业务ID
     */
    private Long buzId;

    public EmailBean() {
    }

    public EmailBean(String email, Long templateId, EmailLogType emailLogType, String subject, String content,
                     Map<String, String> emailC) {
        this.email = email;
        this.templateId = templateId;
        this.emailLogType = emailLogType;
        this.subject = subject;
        this.content = content;
        this.emailC = emailC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public EmailLogType getEmailLogType() {
        return emailLogType;
    }

    public void setEmailLogType(EmailLogType emailLogType) {
        this.emailLogType = emailLogType;
    }

    public Map<String, String> getEmailC() {
        return emailC;
    }

    public void setEmailC(Map<String, String> emailC) {
        this.emailC = emailC;
    }

    public Long getBuzId() {
        return buzId;
    }

    public void setBuzId(Long buzId) {
        this.buzId = buzId;
    }

}

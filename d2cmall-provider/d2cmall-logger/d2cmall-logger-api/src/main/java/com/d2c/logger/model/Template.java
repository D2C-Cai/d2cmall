package com.d2c.logger.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;

/**
 * 模板
 */
@Table(name = "template")
public class Template extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主题
     */
    @AssertColumn("模板标题不能为空")
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 模版字符串
     */
    private String templateType;
    /**
     * 创建人
     */
    private String createMan;
    /**
     * 是否是固定模板，固定模板不能删除
     */
    private Boolean fixed;
    /**
     * 参数说明
     */
    private String paramInfo;

    /**
     * 必填变量不能为空
     */
    public boolean isNotBlank() {
        return StringUtils.isNotBlank(subject) && StringUtils.isNotBlank(content) && templateType != null
                && fixed != null;
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

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public String getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(String paramInfo) {
        this.paramInfo = paramInfo;
    }

    /**
     * 邮件，短信,文章
     */
    public static enum EnumTemplateType {
        EMAIL, SMS, ARTICLE
    }

}

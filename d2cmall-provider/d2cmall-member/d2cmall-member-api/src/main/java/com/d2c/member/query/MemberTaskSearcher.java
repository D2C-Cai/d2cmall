package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class MemberTaskSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 是否启用
     */
    private Boolean exec;
    /**
     * 是否固定，不允许删除
     */
    private Boolean fixed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getExec() {
        return exec;
    }

    public void setExec(Boolean exec) {
        this.exec = exec;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

}

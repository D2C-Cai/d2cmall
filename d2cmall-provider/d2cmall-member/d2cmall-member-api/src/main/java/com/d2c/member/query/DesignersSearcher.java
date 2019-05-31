package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class DesignersSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 设计师姓名
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 品牌id
     */
    private Long designerId;
    /**
     * 状态
     */
    private Integer status;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

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

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

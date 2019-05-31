package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -pc wap模块定义
 */
@Table(name = "v_field_define")
public class FieldDefine extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面定义ID
     */
    private Long pageDefId;
    /**
     * blockXX
     */
    private String code;
    /**
     * 别名
     */
    private String aliasCode;
    /**
     * 0,自定义代码，1，块添加
     */
    private Integer type = 0;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片张数
     */
    private Integer picSize = 2;
    /**
     * 可用
     */
    private Integer status = -1;

    public Integer getPicSize() {
        return picSize;
    }

    public void setPicSize(Integer picSize) {
        this.picSize = picSize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPageDefId() {
        return pageDefId;
    }

    public void setPageDefId(Long pageDefId) {
        this.pageDefId = pageDefId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

}

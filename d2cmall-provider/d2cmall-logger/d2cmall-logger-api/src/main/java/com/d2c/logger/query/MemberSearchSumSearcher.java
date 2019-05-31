package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.logger.model.MemberSearchSum.MemberSearchSumType;

public class MemberSearchSumSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 是否系统
     */
    private Boolean system;
    /**
     * 类型
     */
    private MemberSearchSumType type;
    /**
     * APP首页分类ID
     */
    private Long subModuleCategoryId;
    /**
     * 状态
     */
    private Integer status;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public MemberSearchSumType getType() {
        return type;
    }

    public void setType(MemberSearchSumType type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSubModuleCategoryId() {
        return subModuleCategoryId;
    }

    public void setSubModuleCategoryId(Long subModuleCategoryId) {
        this.subModuleCategoryId = subModuleCategoryId;
    }

}

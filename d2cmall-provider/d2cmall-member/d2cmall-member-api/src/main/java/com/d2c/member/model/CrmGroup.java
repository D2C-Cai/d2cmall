package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * CRM小组
 */
@Table(name = "m_crm_group")
public class CrmGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    @AssertColumn("名称不能为空")
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态
     */
    private Integer status = 1;
    /**
     * 排序
     */
    private Integer sort = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}

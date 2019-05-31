package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseUserDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 相似商品方案表
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "similar_scheme")
public class SimilarSchemeDO extends BaseUserDO {

    private static final long serialVersionUID = 7902814112969375973L;
    private String schemeName;
    @Column(updatable = false)
    private Integer categoryId;
    private Integer maxSize;
    private Boolean hasExec = false;
    private String remark;

    @Override
    public String toString() {
        return schemeName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Boolean getHasExec() {
        return hasExec;
    }

    public void setHasExec(Boolean hasExec) {
        this.hasExec = hasExec;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

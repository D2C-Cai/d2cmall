package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 杂志页模板
 */
@Table(name = "m_magazine_page")
public class MagazinePage extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 状态 0停用 1启用
     */
    private Integer status;
    /**
     * 杂志定义ID
     */
    private Long magazineId;
    /**
     * 手机版内容
     */
    private String mobileContent;
    /**
     * 页码
     */
    private Integer sort;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public String getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(String mobileContent) {
        this.mobileContent = mobileContent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("code", this.getCode());
        obj.put("magazineId", this.getMagazineId());
        return obj;
    }

}

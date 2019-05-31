package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;

/**
 * 搜索关键字
 */
@Table(name = "membersearchsum")
public class MemberSearchSum extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 是否系统
     */
    private Boolean system = false;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 展示名称
     */
    private String displayName;
    /**
     * 搜索次数
     */
    private Integer number = 0;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 类型
     */
    private String type;
    /**
     * 源ID
     */
    private String sourceId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 页面跳转链接
     */
    private String url;
    /**
     * APP页分类ID
     */
    private Long subModuleCategoryId;
    /**
     * APP页分类名称
     */
    private String subModuleName;

    public String getMemberSearcherSumTypeString() {
        if (this.type == null)
            return "不限";
        MemberSearchSumType type = MemberSearchSumType.findEnumByName(this.type);
        switch (type) {
            case TAG:
                return "商品";
            case DESIGNER:
                return "设计师";
            case CATEGORY:
                return "分类";
            case TOPCATEGORY:
                return "一级分类";
            case ALL:
                return "不限";
        }
        return "";
    }

    public boolean check() {
        return StringUtils.isBlank(keyword);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getUrl() {
        if (StringUtils.isBlank(this.url)) {
            this.url = "/product/list?k=" + getKeyword();
        }
        return this.url;
    }

    public void setUrl(String url) {
        if (StringUtils.isBlank(url)) {
            url = "/product/list?k=" + getKeyword();
        }
        this.url = url;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("keyword", this.getKeyword());
        obj.put("number", this.getNumber());
        obj.put("sort", this.getSort());
        obj.put("displayName", this.getDisplayName());
        obj.put("system", this.getSystem());
        obj.put("type", this.getType());
        obj.put("sourceId", this.getSourceId());
        obj.put("status", this.getStatus());
        obj.put("url", this.getUrl());
        return obj;
    }

    public static enum MemberSearchSumType {
        DESIGNER, ALL, CATEGORY, TAG, TOPCATEGORY;

        public static MemberSearchSumType findEnumByName(String name) {
            if (name == null || name.length() == 0) {
                return null;
            }
            for (MemberSearchSumType temp : MemberSearchSumType.values()) {
                if (name.equals(temp.name())) {
                    return temp;
                }
            }
            return null;
        }
    }

}

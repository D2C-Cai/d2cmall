package com.d2c.logger.search.model;

import com.d2c.common.api.dto.PreUserDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * 会员搜索信息汇总
 */
public class SearcherMemberSum extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 是否是系统创建
     */
    private Boolean system;
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
    private Integer number;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 类型
     */
    private String type;
    /**
     * 来源ID
     */
    private String sourceId;

    public String getMemberSearcherSumTypeString() {
        if (this.type == null)
            return "不限";
        switch (this.type) {
            case "DESIGNER":
                return "设计师";
            case "ALL":
                return "不限";
            case "CATEGORY":
                return "分类";
            case "TOPCATEGORY":
                return "一级分类";
            default:
                break;
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

}

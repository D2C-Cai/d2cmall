package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.content.model.FieldDefine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDefineSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 预览地址
     */
    private String previewUrl;
    /**
     * 模块编码
     */
    private String module;
    /**
     * 模块描述
     */
    private String description;
    /**
     * 终端
     */
    private TERMINAL terminal;
    /**
     * 版本号
     */
    private Integer version;
    private Map<String, FieldDefine> fieldDefs;
    private List<FieldDefine> fieldList;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTerminal() {
        if (terminal != null) {
            return terminal.toString();
        }
        return null;
    }

    public void setTerminal(TERMINAL terminal) {
        this.terminal = terminal;
    }

    public Map<String, FieldDefine> getFieldDefs() {
        if (fieldDefs == null) {
            fieldDefs = new HashMap<String, FieldDefine>();
        }
        return fieldDefs;
    }

    public void setFieldDefs(Map<String, FieldDefine> fieldDefs) {
        this.fieldDefs = fieldDefs;
    }

    public List<FieldDefine> getFieldList() {
        if (fieldList == null) {
            fieldList = new ArrayList<FieldDefine>();
        }
        return fieldList;
    }

    public void setFieldList(List<FieldDefine> fieldList) {
        this.fieldList = fieldList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public static enum MODULE {
        HOMEPAGE, STAR, CUSTOM, SHOWROOM, PROMOTITION, MALE, FEMALE;

        public String toString() {
            String module = "未知";
            if (this.equals(HOMEPAGE)) {
                module = "首页";
            } else if (this.equals(STAR)) {
                module = "明细风范";
            } else if (this.equals(CUSTOM)) {
                module = "私人定制";
            } else if (this.equals(SHOWROOM)) {
                module = "设计师品牌";
            } else if (this.equals(PROMOTITION)) {
                module = "活动";
            } else if (this.equals(MALE)) {
                module = "男士";
            } else if (this.equals(FEMALE)) {
                module = "女士";
            }
            return module;
        }
    }

    public static enum TERMINAL {
        PC, TABLET, MOBILE, DISTRIBUTION;

        public static TERMINAL getEnumByName(String name) {
            if (name == null || name.length() == 0) {
                return null;
            }
            for (TERMINAL temp : TERMINAL.values()) {
                if (temp.name().equals(name)) {
                    return temp;
                }
            }
            return null;
        }
    }

}

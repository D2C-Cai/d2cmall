package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -pc wap页面定义
 */
@Table(name = "v_page_define")
public class PageDefine extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 预览地址
     */
    private String previewUrl;
    /**
     * 模块编码
     */
    @AssertColumn("模块编码不能为空")
    private String module;
    /**
     * 模块描述
     */
    private String description;
    /**
     * 终端
     */
    private String terminal;
    /**
     * 版本号
     */
    private Integer version = 1;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public static enum MODULE {
        HOMEPAGE, STAR, CUSTOM, SHOWROOM;

        public static MODULE getEnumByName(String name) {
            if (name == null || name.length() == 0) {
                return null;
            }
            for (MODULE temp : MODULE.values()) {
                if (temp.name().equals(name)) {
                    return temp;
                }
            }
            return null;
        }

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
            }
            return module;
        }
    }

    public static enum TERMINAL {
        PC, TABLET, MOBILE;

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

package com.d2c.content.search.query;

import java.io.Serializable;

public class SectionSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 页面的ID
     */
    private Long moduleId;
    /**
     * 版块的ID
     */
    private Long sectionDefId;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 会员级别
     */
    private String memberLevel;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getSectionDefId() {
        return sectionDefId;
    }

    public void setSectionDefId(Long sectionDefId) {
        this.sectionDefId = sectionDefId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

}

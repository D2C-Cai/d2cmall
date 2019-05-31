package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -app板块
 */
@Table(name = "v_section")
public class Section extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面的ID
     */
    @AssertColumn("对于模块ID不能为空")
    private Long moduleId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标题是否可见
     */
    private Integer visible = 0;
    /**
     * 类型(100之内为系统内置，无需定义内容，新增之后，不允许修改)
     */
    @AssertColumn("类型不能为空")
    private Integer type = 0;
    /**
     * 顺序
     */
    private Integer sequence = 0;
    /**
     * 扩展的值
     */
    private String properties;
    /**
     * 是否固定
     */
    private Integer fixed = 0;
    /**
     * 显示更多
     */
    private Integer more;
    /**
     * 更多链接
     */
    private String moreUrl;
    /**
     * 可查看会员级别
     */
    private String memberLevel = "N";

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String[] getShowLevel() {
        if (memberLevel != null) {
            memberLevel.split(",");
        }
        return new String[0];
    }

}

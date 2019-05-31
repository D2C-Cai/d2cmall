package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 后台菜单
 */
@Table(name = "m_resource")
public class Resource extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("资源名称不能为空")
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 是否内置
     */
    private Integer internal = 0;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 树路径
     */
    private String path;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 深度
     */
    private Integer depth = 0;
    /**
     * 前端url地址
     */
    private String webUrl;
    /**
     * 图片名称
     */
    private String imgName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getInternal() {
        return internal;
    }

    public void setInternal(Integer internal) {
        this.internal = internal;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public static enum ResourceType {
        DIR, URL, METHOD
    }

}
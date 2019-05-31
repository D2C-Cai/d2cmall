package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;

/**
 * 实体类 -导航
 */
@Table(name = "v_navigation")
public class Navigation extends PreUserDO {

    /**
     * 树路径分隔符
     */
    public final static String PATH_SEPARATOR = ",";
    private static final long serialVersionUID = 1L;
    /**
     * 别名
     */
    @AssertColumn("导航编号不能为空")
    private String code;
    /**
     * 名称
     */
    @AssertColumn("导航名称不能为空")
    private String name;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 树路径
     */
    private String path;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 状态
     */
    private Integer status = 1;
    /**
     * CSS样式
     */
    private String cssStyle = null;
    /**
     * 导航图片
     */
    private String pic;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 版本
     */
    private String version = "v3";

    public String generateSQL() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return path.split(PATH_SEPARATOR).length - 1;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        obj.put("name", getName());
        obj.put("url", this.getUrl());
        obj.put("pic", StringUtils.isNotBlank(this.getPic()) ? this.getPic() : "");
        obj.put("picUrl", StringUtils.isNotBlank(this.getPicUrl()) ? this.getPicUrl() : "");
        return obj;
    }

}
package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ResourceSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 是否内置
     */
    private Integer internal;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 深度
     */
    private Integer depth;
    /**
     * 深度小于depthLess
     */
    private Integer depthLess;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDepthLess() {
        return depthLess;
    }

    public void setDepthLess(Integer depthLess) {
        this.depthLess = depthLess;
    }

}

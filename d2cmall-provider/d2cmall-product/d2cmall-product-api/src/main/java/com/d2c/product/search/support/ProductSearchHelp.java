package com.d2c.product.search.support;

import java.io.Serializable;

/**
 * 搜索帮助
 *
 * @author Lain
 */
public class ProductSearchHelp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 来源ID
     */
    private Long sourceId;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级ID
     */
    private String parentId;

    public ProductSearchHelp() {
    }

    public ProductSearchHelp(Long sourceId, String type, String name, String parentId) {
        this.sourceId = sourceId;
        this.type = type;
        this.name = name;
        this.parentId = parentId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}

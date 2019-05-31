package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class SalesPropertyGroupSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态：0停用，1启用
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

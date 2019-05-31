package com.d2c.product.search.model;

import java.io.Serializable;

public class SearcherTopCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    protected Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 代号
     */
    private String code;
    /**
     * 排序
     */
    private Integer sequence;
    /**
     * 停用 0,启用 1
     */
    private Integer status;
    /**
     * 图片
     */
    private String pic;
    /**
     * 英文名
     */
    private String eName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

}

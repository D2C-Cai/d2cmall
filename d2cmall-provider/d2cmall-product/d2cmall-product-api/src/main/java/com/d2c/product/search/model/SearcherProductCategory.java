package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class SearcherProductCategory implements Serializable {

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
     * 树路径
     */
    private String paths;
    /**
     * 大类ID
     */
    private Long topId;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 深度(=1为初级 =n则为子级)
     */
    private Integer depth;
    /**
     * 是否末级
     */
    private Integer leaf;
    /**
     * 停用 0/启用 1
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

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
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

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("code", this.getCode());
        obj.put("pic", this.getPic());
        obj.put("topId", this.getTopId());
        obj.put("parentId", this.getParentId());
        return obj;
    }

}

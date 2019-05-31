package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.product.search.query.DesignerSearchBean;
import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

public class SearcherDesignerTag implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上下架 1上架，0下架
     */
    private Integer status;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("sort", this.getSort());
        return obj;
    }

    public DesignerSearchBean convert2TagDesignerSearchBean() {
        DesignerSearchBean searcherBean = new DesignerSearchBean();
        searcherBean.setTagId(this.getId());
        String[] sortFields = new String[]{"tagSort"};
        SortOrder[] sortOrders = new SortOrder[]{SortOrder.DESC};
        searcherBean.setOrders(sortOrders);
        searcherBean.setSortFields(sortFields);
        searcherBean.setMark(1);
        return searcherBean;
    }

}

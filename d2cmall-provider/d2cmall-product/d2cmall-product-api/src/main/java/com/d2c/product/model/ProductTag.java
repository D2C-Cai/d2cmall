package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 商品标签
 */
@Table(name = "p_product_tag")
public class ProductTag extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签名称
     */
    @AssertColumn("商品条码不能为空")
    private String name;
    /**
     * 是否是固定标签，固定标签不能删除
     */
    private Integer fixed = 0;
    /**
     * 上下架 1上架 0下架
     */
    private Integer status = 0;
    /**
     * 上架时间（按这个排序）
     */
    private Date upDateTime;
    /**
     * 页面标签
     */
    private String type = TagType.NORMAL.toString();
    /**
     * 排序
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public enum TagType {
        NORMAL, STAR
    }

}

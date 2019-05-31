package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 礼物商品
 */
@Table(name = "p_product_present")
public class Present extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @AssertColumn("标题不能为空")
    private String name;
    /**
     * 礼物价格
     */
    @AssertColumn(value = "礼物价格输入不正确", min = 0)
    private BigDecimal price;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 礼物图片
     */
    private String pic;
    /**
     * 状态，0-关闭，1-启用
     */
    private Integer status = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("price", this.getPrice());
        json.put("sort", this.getSort());
        json.put("pic", this.getPic());
        return json;
    }

}

package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 组合商品
 */
@Table(name = "p_product_comb")
public class ProductComb extends RichTextBaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * D2C货号
     */
    private String inernalSn;
    /**
     * 外部货号/设计师货号
     */
    private String externalSn;
    /**
     * 商品名称
     */
    @AssertColumn("商品名称不能为空")
    private String name;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 商品状态标志（0:下架，1：上架,-1 删除）
     */
    private Integer mark = 0;
    /**
     * 上架时间
     */
    private Date upMarketDate = null;
    /**
     * 下架时间
     */
    private Date downMarketDate = null;
    /**
     * 售价
     */
    @AssertColumn("商品售价不能为空")
    private BigDecimal price;
    /**
     * 吊牌价
     */
    @AssertColumn(value = "商品价格不允许小于等于0", min = 0)
    private BigDecimal originalCost;

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("inernalSN", inernalSn);
        obj.put("name", name);
        obj.put("subTitle", subTitle);
        obj.put("id", id);
        obj.put("price", price);
        obj.put("originalCost", originalCost);
        obj.put("mark", mark);
        return obj;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getDownMarketDate() {
        return downMarketDate;
    }

    public void setDownMarketDate(Date downMarketDate) {
        this.downMarketDate = downMarketDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}

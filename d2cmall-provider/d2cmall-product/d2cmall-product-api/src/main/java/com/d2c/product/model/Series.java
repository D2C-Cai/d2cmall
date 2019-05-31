package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 品牌系列
 */
@Table(name = "p_brand_series")
public class Series extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 系列描述
     */
    private String description;
    /**
     * 设计灵感
     */
    private String inspiration;
    /**
     * 系列XX
     */
    private String seriesXx;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 季节
     */
    private String season;
    /**
     * 系列上架时间
     */
    private Date upDateTime = new Date();
    /**
     * 风格
     */
    private String style;
    /**
     * 价格段
     */
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInspiration() {
        return inspiration;
    }

    public void setInspiration(String inspiration) {
        this.inspiration = inspiration;
    }

    public String getSeriesXx() {
        return seriesXx;
    }

    public void setSeriesXx(String seriesXx) {
        this.seriesXx = seriesXx;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("description", this.getDescription());
        json.put("inspiration", this.getInspiration());
        json.put("seriesXx", this.getSeriesXx());
        json.put("season", this.getSeason());
        json.put("upDateTime", this.getUpDateTime());
        return json;
    }

}

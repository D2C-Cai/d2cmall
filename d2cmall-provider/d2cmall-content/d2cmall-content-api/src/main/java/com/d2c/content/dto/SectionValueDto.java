package com.d2c.content.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.content.model.SectionValue;
import org.apache.commons.lang3.StringUtils;

public class SectionValueDto extends SectionValue {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 标题是否可见
     */
    private Integer visible = 0;
    /**
     * 类型(100之内为系统内置，无需定义内容，新增之后，不允许修改)
     */
    private Integer types = 0;
    /**
     * 是否固定
     */
    private Integer fixed = 0;
    /**
     * 顺序
     */
    private Integer sort = 0;
    /**
     * 扩展的值
     */
    private String prop;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public JSONObject toInitJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("sectionDefId", this.getSectionDefId());
        json.put("shortTitle", this.getShortTitle());
        json.put("longTitle", this.getLongTitle());
        json.put("price", this.getPrice());
        json.put("originalPrice", this.getOriginalPrice());
        json.put("brand", this.getBrand());
        json.put("url", this.getUrl());
        json.put("frontPic", this.getFrontPic());
        json.put("endPic", this.getEndPic());
        json.put("videoPic", this.getVideoPic());
        json.put("videoPath", this.getVideoPath());
        json.put("sequence", this.getSequence());
        json.put("relationType", this.getRelationType());
        json.put("relationId", this.getRelationId());
        json.put("relationId", this.getRelationId());
        json.put("tags", this.getTagsList());
        return json;
    }

    private JSONArray getTagsList() {
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(this.getTags())) {
            String[] tags = this.getTags().split(",");
            for (String tag : tags) {
                if (StringUtils.isBlank(tag)) {
                    continue;
                }
                array.add(tag);
            }
        }
        return array;
    }

}

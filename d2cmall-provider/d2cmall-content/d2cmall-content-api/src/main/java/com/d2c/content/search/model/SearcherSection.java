package com.d2c.content.search.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.Section;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class SearcherSection implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private String id;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * ******************** SectionValue ********************
     */
    /**
     * 页面的ID
     */
    private Long moduleId;
    /**
     * 版块的ID
     */
    private Long sectionDefId;
    /**
     * 短标题
     */
    private String shortTitle;
    /**
     * 长标题
     */
    private String longTitle;
    /**
     * 价格
     */
    private String price;
    /**
     * 原价
     */
    private String originalPrice;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 绑定地址
     */
    private String url;
    /**
     * 首图
     */
    private String frontPic;
    /**
     * 背面图
     */
    private String endPic;
    /**
     * 视频图片地址
     */
    private String videoPic;
    /**
     * 视频播放地址
     */
    private String videoPath;
    /**
     * 顺序
     */
    private Integer sequence = 0;
    /**
     * 类型(100之内为系统内置，无需定义内容，新增之后，不允许修改)
     */
    private Integer type = 0;
    /**
     * 扩展的值
     */
    private String properties;
    /**
     * 上下架
     */
    private Integer status;
    /**
     * 查看人次
     */
    private Integer watched;
    /**
     * 关联类型、用于app请求品牌还是活动
     */
    private String relationType;
    /**
     * 关联ID
     */
    private Long relationId;
    /**
     * 标签
     */
    private String tags;
    /**
     * ******************** Section ********************
     */
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
    /**
     * 显示更多
     */
    private Integer more = 0;
    /**
     * 更多链接
     */
    private String moreUrl;
    /**
     * 会员级别
     */
    private String memberLevel;
    /**
     * ******************** SectionValueList ********************
     */
    /**
     * 将有嵌套列表的块信息转成JSON存在此处
     */
    private String sectionValues;

    public SearcherSection() {
    }

    public SearcherSection(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getSectionDefId() {
        return sectionDefId;
    }

    public void setSectionDefId(Long sectionDefId) {
        this.sectionDefId = sectionDefId;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getEndPic() {
        return endPic;
    }

    public void setEndPic(String endPic) {
        this.endPic = endPic;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getSectionValues() {
        return sectionValues;
    }

    public void setSectionValues(String sectionValues) {
        this.sectionValues = sectionValues;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void initFix(Section section, int version) {
        this.setId(section.getId() + "_" + 0L + "_" + version);
        this.setStatus(1);
        this.setVersion(version);
        this.setCreateDate(section.getCreateDate());
        this.setMemberLevel(section.getMemberLevel());
        this.setVisible(section.getVisible());
        this.setFixed(section.getFixed());
        this.setSort(section.getSequence());
        this.setModuleId(section.getModuleId());
        this.setSectionDefId(section.getId());
        this.setTitle(section.getTitle());
        this.setMore(section.getMore());
        this.setMoreUrl(section.getMoreUrl());
        //
        this.setTypes(section.getType());
        this.setProp(section.getProperties());
    }

    public void initUnFix(Section section, SectionValueDto dto, int version, int pageNum) {
        this.setId(section.getId() + "_" + dto.getId() + "_" + version);
        this.setStatus(dto.getStatus());
        this.setVersion(version);
        this.setCreateDate(dto.getCreateDate());
        this.setMemberLevel(section.getMemberLevel());
        this.setVisible(pageNum == 1 ? section.getVisible() : 0);
        this.setFixed(section.getFixed());
        this.setSort(section.getSequence());
        this.setModuleId(section.getModuleId());
        this.setSectionDefId(dto.getSectionDefId());
        this.setTitle(section.getTitle());
        this.setMore(section.getMore());
        this.setMoreUrl(section.getMoreUrl());
        //
        this.setType(dto.getType());
        this.setProperties(dto.getProperties());
        this.setShortTitle(dto.getShortTitle());
        this.setLongTitle(dto.getLongTitle());
        this.setPrice(dto.getPrice());
        this.setOriginalPrice(dto.getOriginalPrice());
        this.setBrand(dto.getBrand());
        this.setUrl(dto.getUrl());
        this.setFrontPic(dto.getFrontPic());
        this.setEndPic(dto.getEndPic());
        this.setVideoPic(dto.getVideoPic());
        this.setVideoPath(dto.getVideoPath());
        this.setSequence(dto.getSequence());
        this.setTags(dto.getTags());
    }

    /**
     * 固定模块渲染定义为section字段，其余取sectionValues字段JSON
     */
    public JSONObject toFixJson() {
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("moduleId", this.getModuleId());
        json.put("title", this.getTitle());
        json.put("visible", this.getVisible());
        json.put("sequence", this.getSort());
        // section的type
        json.put("type", this.getTypes());
        // section的properties
        json.put("properties", this.getProp() != null ? JSONObject.parseObject(this.getProp()) : new JSONObject());
        // 对应的list
        json.put("sectionValues",
                this.getSectionValues() != null ? JSONArray.parseArray(this.getSectionValues()) : new JSONArray());
        json.put("more", this.getMore());
        json.put("moreUrl", this.getMoreUrl());
        if (this.getType() == 11) {
            json.put("shortTitle", this.getShortTitle());
            json.put("frontPic", this.getSort());
            json.put("url", this.getUrl());
        }
        return json;
    }

    /**
     * 固定模块渲染定义为sectionValue字段，其余取sectionValues字段JSON
     */
    public JSONObject toUnFixJson() {
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("moduleId", this.getModuleId());
        json.put("title", this.getTitle());
        json.put("visible", this.getVisible());
        json.put("sequence", this.getSort());
        // sectionValue的type
        json.put("type", this.getType());
        // sectionValue的properties
        json.put("properties",
                this.getProperties() != null ? JSONObject.parseObject(this.getProperties()) : new JSONObject());
        JSONArray array = new JSONArray();
        JSONObject json2 = new JSONObject();
        json2.put("id", 1);
        json2.put("sectionDefId", this.getSectionDefId());
        json2.put("shortTitle", this.getShortTitle());
        json2.put("longTitle", this.getLongTitle());
        json2.put("price", this.getPrice());
        json2.put("originalPrice", this.getOriginalPrice());
        json2.put("brand", this.getBrand());
        json2.put("url", this.getUrl());
        json2.put("frontPic", this.getFrontPic());
        json2.put("endPic", this.getEndPic());
        json2.put("videoPic", this.getVideoPic());
        json2.put("videoPath", this.getVideoPath());
        json2.put("sequence", this.getSequence());
        // 对应的list
        json2.put("list",
                this.getSectionValues() != null ? JSONArray.parseArray(this.getSectionValues()) : new JSONArray());
        array.add(json2);
        json.put("sectionValues", array);
        json.put("watched", this.getWatched() != null ? this.getWatched() : 0);
        json.put("more", this.getMore());
        json.put("moreUrl", this.getMoreUrl());
        json.put("tags", getTagsList());
        return json;
    }

    private JSONArray getTagsList() {
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(this.tags)) {
            String[] tags = this.tags.split(",");
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

package com.d2c.content.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -app板块内容
 */
@Table(name = "v_section_value")
public class SectionValue extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面的ID
     */
    @AssertColumn("模块ID不能为空")
    private Long moduleId;
    /**
     * 版块的ID
     */
    @AssertColumn("版块ID不能为空")
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
     * 类型(100之内为系统内置，无需定义内容，新增之后，不允许修改)<br>
     * 1轮播图 3排列 4多块 6 单一块 <br>
     * 11大型活动 13 明星风范 14 运营位1 15运营位2<br>
     * 16限时购 17图标签 18上新19运营位3（限时购）
     */
    @AssertColumn("类型不能为空")
    private Integer type = 0;
    /**
     * 扩展的值
     */
    @AssertColumn("参数不能为空")
    private String properties;
    /**
     * 上下架
     */
    private Integer status = 1;
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
     * 是否删除
     */
    private Integer deleted;
    /**
     * 定时上下架
     */
    private Integer timing = 0;
    /**
     * 定时上架时间
     */
    private Date startTime;
    /**
     * 定时下架时间
     */
    private Date endTime;
    /**
     * 标签
     */
    private String tags;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("moduleId", this.getModuleId());
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
        array.add(json2);
        json.put("sectionValues", array);
        json.put("watched", this.getWatched() != null ? this.getWatched() : 0);
        return json;
    }

    public enum RelationType {
        PROMOTION, BRAND
    }

}

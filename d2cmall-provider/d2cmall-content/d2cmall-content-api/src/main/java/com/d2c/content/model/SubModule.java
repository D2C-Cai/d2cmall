package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;

/**
 * 实体类 -app页面
 */
@Table(name = "v_sub_module")
public class SubModule extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * APP的频道
     */
    private String parent = ParentEnum.MAIN.name();
    /**
     * 方向（（TOP）上，（BOTTOM）下，（LEFT）左、（RIGHT）右）
     */
    private String direction = "TOP";
    /**
     * 标题
     */
    private String title;
    /**
     * ajax请求地址
     */
    private String url;
    /**
     * web浏览器打开地址
     */
    private String webUrl;
    /**
     * 颜色
     */
    private String color = "000000";
    /**
     * 顺序,由小到大
     */
    private Integer sequence = 0;
    /**
     * 同一级，只能有一个是默认状态，如果没有，则第一个是
     */
    private Integer isDefault = 0;
    /**
     * 0下架 1上架
     */
    private Integer status = 0;
    /**
     * 版本号 每次发布+1
     */
    private Integer version = 1;
    /**
     * 分类ID,可以为空<br>
     * 用于首页<br>
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 是否是分类
     */
    private Integer isCategory = 0;
    /**
     * 标题选中图片
     */
    private String tPic;
    /**
     * 标题未选中图片
     */
    private String tbPic;
    /**
     * 背景图片
     */
    private String backPic;
    /**
     * 背景颜色
     */
    private String backColor;
    /**
     * 背景类型
     */
    private String backType;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(Integer isCategory) {
        this.isCategory = isCategory;
    }

    public String gettPic() {
        return tPic;
    }

    public void settPic(String tPic) {
        this.tPic = tPic;
    }

    public String getTbPic() {
        return tbPic;
    }

    public void setTbPic(String tbPic) {
        this.tbPic = tbPic;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getParentName() {
        if (StringUtil.isBlank(this.parent)) {
            return "";
        }
        return ParentEnum.valueOf(this.parent).getDisplay();
    }

    public void setParentName() {
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("parent", this.getParent());
        json.put("direction", this.getDirection());
        json.put("title", this.getTitle());
        json.put("webUrl", this.getWebUrl());
        json.put("color", this.getColor());
        json.put("sequence", this.getSequence());
        json.put("isDefault", this.getIsDefault());
        json.put("isCategory", this.getIsCategory());
        json.put("categoryId", this.getCategoryId());
        json.put("tPic", this.gettPic());
        json.put("tbPic", this.getTbPic());
        return json;
    }

    enum BackTypeEnum {
        COLOR, PIC
    }

    public enum ParentEnum {
        MAIN("首页2.0"), HOME("首页3.0"), SQUARE("广场"), LIVE("直播");
        private String display;

        ParentEnum(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}

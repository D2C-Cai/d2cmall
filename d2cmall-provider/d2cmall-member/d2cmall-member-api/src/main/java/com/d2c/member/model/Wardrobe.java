package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 衣橱明细
 *
 * @author Lain
 */
@Table(name = "m_wardrobe")
public class Wardrobe extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 图片
     */
    private String pic;
    /**
     * 顶级名称
     */
    private String topName;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 价位
     */
    private String price;
    /**
     * 季节
     */
    private String season;
    /**
     * 场景
     */
    private String scene;
    /**
     * 标签
     */
    private String tags;
    /**
     * 色彩
     */
    private String color;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("loginCode", this.getLoginCode());
        obj.put("nickName", this.getNickName());
        obj.put("pic", this.getPic());
        obj.put("topName", this.getTopName());
        obj.put("categoryId", this.getCategoryId());
        obj.put("categoryName", this.getCategoryName());
        obj.put("price", this.getPrice());
        obj.put("season", this.getSeason());
        obj.put("scene", this.getScene());
        obj.put("tags", this.getTags());
        obj.put("color", this.getColor());
        return obj;
    }

}

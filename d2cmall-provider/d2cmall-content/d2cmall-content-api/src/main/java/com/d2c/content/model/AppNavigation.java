package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * app底部导航栏
 *
 * @author Administrator
 */
@Table(name = "v_app_navigation")
public class AppNavigation extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 原图
     */
    private String pic;
    /**
     * 点击后图片
     */
    private String clickPic;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 背景颜色
     */
    private String backColor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getClickPic() {
        return clickPic;
    }

    public void setClickPic(String clickPic) {
        this.clickPic = clickPic;
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

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.getName());
        obj.put("pic", this.getPic());
        obj.put("clickPic", this.getClickPic());
        obj.put("sort", this.getSort());
        obj.put("status", this.getStatus());
        obj.put("backColor", this.getBackColor());
        return obj;
    }

}

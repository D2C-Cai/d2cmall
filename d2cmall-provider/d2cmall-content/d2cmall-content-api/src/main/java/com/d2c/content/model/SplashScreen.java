package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -app闪屏
 */
@Table(name = "v_splash_screen")
public class SplashScreen extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主题
     */
    private String name;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 定时上下架
     */
    private Integer timing = 0;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 320x480
     */
    private String pic320480;
    /**
     * 320x568
     */
    private String pic320568;
    /**
     * 375x667
     */
    private String pic375667;
    /**
     * 414x736
     */
    private String pic414736;
    /**
     * 375x812
     */
    private String pic375812;
    /**
     * 图片Url
     */
    private String urls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic320480() {
        return pic320480;
    }

    public void setPic320480(String pic320480) {
        this.pic320480 = pic320480;
    }

    public String getPic320568() {
        return pic320568;
    }

    public void setPic320568(String pic320568) {
        this.pic320568 = pic320568;
    }

    public String getPic375667() {
        return pic375667;
    }

    public void setPic375667(String pic375667) {
        this.pic375667 = pic375667;
    }

    public String getPic414736() {
        return pic414736;
    }

    public void setPic414736(String pic414736) {
        this.pic414736 = pic414736;
    }

    public String getPic375812() {
        return pic375812;
    }

    public void setPic375812(String pic375812) {
        this.pic375812 = pic375812;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        return json;
    }

}

package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 埋点事件统计表
 *
 * @author wull
 */
@Document
public class EventStatDO extends BaseMongoDO {

    private static final long serialVersionUID = -7223788330791673547L;
    /**
     * 事件名称
     */
    @Indexed
    private String event;
    /**
     * 今日PV页面浏览数量
     */
    private Integer todayPv = 0;
    /**
     * 今日UV独立访客数量
     */
    private Integer todayUv = 0;
    /**
     * 昨日PV页面浏览数量
     */
    private Integer yesterdayPv = 0;
    /**
     * 昨日UV独立访客数量
     */
    private Integer yesterdayUv = 0;
    /**
     * 总PV页面浏览数量
     */
    private Integer pv = 0;
    /**
     * 总UV独立访客数量
     */
    private Integer uv = 0;
    /**
     * 每日页面浏览数量数据图表
     */
    private List<JSONObject> dayPageViews;
    /**
     * 每日独立访客数量数据图表
     */
    private List<JSONObject> dayUniqueVisitors;
    /**
     * 最近访客
     */
    private List<JSONObject> visitors;

    public EventStatDO() {
    }

    public EventStatDO(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public List<JSONObject> getDayPageViews() {
        return dayPageViews;
    }

    public void setDayPageViews(List<JSONObject> dayPageViews) {
        this.dayPageViews = dayPageViews;
    }

    public List<JSONObject> getDayUniqueVisitors() {
        return dayUniqueVisitors;
    }

    public void setDayUniqueVisitors(List<JSONObject> dayUniqueVisitors) {
        this.dayUniqueVisitors = dayUniqueVisitors;
    }

    public Integer getTodayPv() {
        return todayPv;
    }

    public void setTodayPv(Integer todayPv) {
        this.todayPv = todayPv;
    }

    public Integer getTodayUv() {
        return todayUv;
    }

    public void setTodayUv(Integer todayUv) {
        this.todayUv = todayUv;
    }

    public Integer getYesterdayPv() {
        return yesterdayPv;
    }

    public void setYesterdayPv(Integer yesterdayPv) {
        this.yesterdayPv = yesterdayPv;
    }

    public Integer getYesterdayUv() {
        return yesterdayUv;
    }

    public void setYesterdayUv(Integer yesterdayUv) {
        this.yesterdayUv = yesterdayUv;
    }

    public List<JSONObject> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<JSONObject> visitors) {
        this.visitors = visitors;
    }

}

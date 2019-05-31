package com.d2c.behavior.mongo.dto;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.common.core.model.KeyValue;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 埋点统计数据
 *
 * @author wull
 */
public class EventStatDTO extends BaseDTO {

    private static final long serialVersionUID = -2361403787015791199L;
    /**
     * 事件名称
     */
    private String event;
    /**
     * 总PV页面浏览数量
     */
    private int pv = 0;
    /**
     * 总UV独立访客数量
     */
    private int uv = 0;
    /**
     * 今日PV页面浏览数量
     */
    private int todayPv = 0;
    /**
     * 今日UV独立访客数量
     */
    private int todayUv = 0;
    /**
     * 昨日PV页面浏览数量
     */
    private int lastDayPv = 0;
    /**
     * 昨日UV独立访客数量
     */
    private int lastDayUv = 0;
    /**
     * 每日页面浏览数量数据图表
     */
    private List<KeyValue> pvTable;
    /**
     * 每日独立访客数量数据图表
     */
    private List<KeyValue> uvTable;
    /**
     * 最近访客
     */
    private List<JSONObject> visitors;

    public EventStatDTO() {
    }

    public EventStatDTO(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getTodayPv() {
        return todayPv;
    }

    public void setTodayPv(int todayPv) {
        this.todayPv = todayPv;
    }

    public int getTodayUv() {
        return todayUv;
    }

    public void setTodayUv(int todayUv) {
        this.todayUv = todayUv;
    }

    public int getLastDayPv() {
        return lastDayPv;
    }

    public void setLastDayPv(int lastDayPv) {
        this.lastDayPv = lastDayPv;
    }

    public int getLastDayUv() {
        return lastDayUv;
    }

    public void setLastDayUv(int lastDayUv) {
        this.lastDayUv = lastDayUv;
    }

    public List<KeyValue> getPvTable() {
        return pvTable;
    }

    public void setPvTable(List<KeyValue> pvTable) {
        this.pvTable = pvTable;
    }

    public List<KeyValue> getUvTable() {
        return uvTable;
    }

    public void setUvTable(List<KeyValue> uvTable) {
        this.uvTable = uvTable;
    }

    public List<JSONObject> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<JSONObject> visitors) {
        this.visitors = visitors;
    }

}

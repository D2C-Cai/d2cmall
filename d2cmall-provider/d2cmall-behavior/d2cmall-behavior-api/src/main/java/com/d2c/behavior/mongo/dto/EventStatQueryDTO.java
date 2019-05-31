package com.d2c.behavior.mongo.dto;

import com.d2c.behavior.mongo.enums.EventQueryType;
import com.d2c.common.api.dto.BaseDTO;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.CacheUt;

import java.util.Date;
import java.util.List;

/**
 * 事件统计查询条件
 *
 * @author wull
 */
public class EventStatQueryDTO extends BaseDTO {

    private static final long serialVersionUID = -2361403787015791199L;
    /**
     * 事件名称
     */
    private String event;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段值
     */
    private Object fieldValue;
    /**
     * 统计日期类型
     */
    private String dateType;
    /**
     * 日期回滚值
     */
    private Integer dateBackNum;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 查询类型
     * 默认查询30天数据
     *
     * @see EventQueryType
     */
    private String queryType;
    /**
     * 查询过滤器
     *
     * @see QueryItem
     */
    private List<QueryItem> querys;

    public EventStatQueryDTO() {
    }

    public EventStatQueryDTO(String event, String fieldName, Object fieldValue) {
        this.event = event;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String cacheKey() {
        return CacheUt.getKey(event, fieldName, fieldValue);
    }
    //******************************************

    public DateType getDateTypeEnum() {
        return DateType.getValueOf(dateType);
    }

    public void setDateTypeEnum(DateType dateType) {
        this.dateType = dateType.name();
    }

    public EventQueryType getQueryTypeEnum() {
        return EventQueryType.getValueOf(queryType);
    }

    public void setQueryTypeEnum(EventQueryType queryType) {
        this.queryType = queryType.name();
    }
    //********************************

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<QueryItem> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QueryItem> querys) {
        this.querys = querys;
    }

    public Integer getDateBackNum() {
        return dateBackNum;
    }

    public void setDateBackNum(Integer dateBackNum) {
        this.dateBackNum = dateBackNum;
    }

}

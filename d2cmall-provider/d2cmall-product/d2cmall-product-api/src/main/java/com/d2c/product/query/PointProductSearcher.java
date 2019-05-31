package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class PointProductSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 开始的开始时间
     */
    private Date minStartTime;
    /**
     * 开始的结束时间
     */
    private Date maxStartTime;
    /**
     * 开始的结束时间
     */
    private Date minEndTime;
    /**
     * 结束的结束时间
     */
    private Date maxEndTime;
    /**
     * 上下架状态
     */
    private Integer mark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getMinStartTime() {
        return minStartTime;
    }

    public void setMinStartTime(Date minStartTime) {
        this.minStartTime = minStartTime;
    }

    public Date getMaxStartTime() {
        return maxStartTime;
    }

    public void setMaxStartTime(Date maxStartTime) {
        this.maxStartTime = maxStartTime;
    }

    public Date getMinEndTime() {
        return minEndTime;
    }

    public void setMinEndTime(Date minEndTime) {
        this.minEndTime = minEndTime;
    }

    public Date getMaxEndTime() {
        return maxEndTime;
    }

    public void setMaxEndTime(Date maxEndTime) {
        this.maxEndTime = maxEndTime;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}

package com.d2c.behavior.mongo.dto;

import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.PersonSessionDO;

import java.util.Date;
import java.util.List;

/**
 * 用户登录统计数据
 *
 * @author wull
 */
public class PersonSessionDTO extends PersonSessionDO {

    private static final long serialVersionUID = -2361403787015791199L;
    /**
     * 最后次访问时间
     */
    private Date lastVisitTime;
    /**
     * 30天内访问次数
     */
    private Long visitCount = 0L;
    /**
     * 30天内访问时长
     */
    private Long allKeepTime = 0L;
    /**
     * 事件列表
     */
    private List<EventDO> events;

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Long getAllKeepTime() {
        return allKeepTime;
    }

    public void setAllKeepTime(Long allKeepTime) {
        this.allKeepTime = allKeepTime;
    }

    public List<EventDO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDO> events) {
        this.events = events;
    }

}

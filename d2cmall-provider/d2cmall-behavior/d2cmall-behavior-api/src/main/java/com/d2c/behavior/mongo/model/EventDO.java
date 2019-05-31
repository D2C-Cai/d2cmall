package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.EmtryMongoDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 埋点事件
 *
 * @author wull
 */
@Document
public class EventDO extends EmtryMongoDO {

    private static final long serialVersionUID = -7223788330791673547L;
    /**
     * Object ID
     */
    @Id
    private String id;
    /**
     * 会话ID
     */
    @Indexed
    private String sessionId;
    /**
     * 修改时间
     */
    @Indexed
    private Date gmtModified;
    /**
     * 埋点事件类型ID
     */
    @Indexed
    private String eventTypeId;
    /**
     * 事件名称
     */
    @Indexed
    private String event;
    /**
     * 是否用户可见
     */
    @Indexed
    private Boolean noShow;
    /**
     * 上一个事件ID
     */
    private String prevId;
    /**
     * 上一个事件名称
     */
    private String prevEvent;
    /**
     * 下个事件ID
     */
    private String nextId;
    /**
     * 下一个事件名称
     */
    private String nextEvent;
    /**
     * 用户ID
     */
    private String personId;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * memberInfoId
     */
    @Indexed
    private Long memberId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * ip地址
     */
    private String ip;
    /**
     * App版本
     */
    private String appFullName;
    /**
     * targetId 目标ID
     */
    @Indexed
    private Object targetId;
    /**
     * 数据对象
     */
    private Object data;
    /**
     * 事件备注
     */
    private String remark;

    public EventDO() {
    }

    public EventDO(PersonSessionDO session) {
        this.sessionId = session.getId();
        this.personId = session.getPersonId();
        this.deviceId = session.getDeviceId();
        this.headImg = session.getHeadImg();
        this.nickname = session.getNickname();
        this.memberId = session.getMemberId();
        PersonDeviceDO device = session.getDevice();
        this.ip = device.getIp();
        if (device.getApp() != null) {
            this.appFullName = device.getApp().toString();
        }
    }
    //*********************************

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getAppFullName() {
        return appFullName;
    }

    public void setAppFullName(String appFullName) {
        this.appFullName = appFullName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getPrevEvent() {
        return prevEvent;
    }

    public void setPrevEvent(String prevEvent) {
        this.prevEvent = prevEvent;
    }

    public String getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(String nextEvent) {
        this.nextEvent = nextEvent;
    }

    public Object getTargetId() {
        return targetId;
    }

    public void setTargetId(Object targetId) {
        this.targetId = targetId;
    }

    public Boolean getNoShow() {
        return noShow;
    }

    public void setNoShow(Boolean noShow) {
        this.noShow = noShow;
    }

}

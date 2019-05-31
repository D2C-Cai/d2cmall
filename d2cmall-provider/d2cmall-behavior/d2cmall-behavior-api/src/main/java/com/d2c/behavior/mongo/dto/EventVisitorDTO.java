package com.d2c.behavior.mongo.dto;

import com.d2c.common.api.dto.BaseDTO;

import java.util.Date;

/**
 * 访问用户数据
 *
 * @author wull
 */
public class EventVisitorDTO extends BaseDTO {

    private static final long serialVersionUID = -2361403787015791199L;
    /**
     * 事件ID
     */
    private String eventId;
    /**
     * 事件名称
     */
    private String event;
    /**
     * 用户ID
     */
    private String personId;
    /**
     * 访问时间
     */
    private Date visitDate;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 数据
     */
    private Object data;

    public EventVisitorDTO() {
    }

    public EventVisitorDTO(String event) {
        this.event = event;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

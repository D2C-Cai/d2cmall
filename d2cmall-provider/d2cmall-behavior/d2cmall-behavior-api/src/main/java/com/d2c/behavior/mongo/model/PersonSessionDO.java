package com.d2c.behavior.mongo.model;

import com.d2c.behavior.mongo.enums.SessionStatus;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * 用户会话
 *
 * @author wull
 */
public class PersonSessionDO extends BaseMongoDO {

    private static final long serialVersionUID = -9004635569855548947L;
    /**
     * 用户ID
     * <p>选填, 不存在可能为微信登录等的匿名访客
     */
    @Indexed
    private String personId;
    /**
     * 设备ID, 即 UDID 设备唯一标识
     * <p>必填, 必定存在设备
     */
    @Indexed
    private String deviceId;
    /**
     * 会员memberInfoId
     */
    private Long memberId;
    /**
     * token 登录码
     */
    private String token;
    /**
     * Session状态
     *
     * @see SessionStatus
     */
    @Indexed
    private String status;
    /**
     * 开始时间
     */
    @Indexed
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 持续时间
     */
    private Long keepTime = 0L;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String headImg;
    /**
     * 用户性别
     */
    private String sex;
    private PersonDeviceDO device;
    private PersonDO person;
    @Transient
    private EventDO lastEvent;

    public PersonSessionDO() {
    }

    public PersonSessionDO(PersonDeviceDO device, PersonDO person, String token) {
        this.status = SessionStatus.START.name();
        this.startTime = new Date();
        this.token = token;
        setPerson(person);
        setDevice(device);
    }

    /**
     * 是否为访客
     */
    public boolean isVisitor() {
        return personId == null;
    }

    public void setEndTime() {
        setEndTime(new Date());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }
    //****************************************

    public void setStatus(String status) {
        this.status = status;
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
        this.keepTime = DateUt.between(startTime, endTime);
    }

    public PersonDeviceDO getDevice() {
        return device;
    }

    public void setDevice(PersonDeviceDO device) {
        if (device != null) {
            this.deviceId = device.getUdid();
        }
        this.device = device;
    }

    public PersonDO getPerson() {
        return person;
    }

    public void setPerson(PersonDO person) {
        if (person != null) {
            this.personId = person.getId();
            this.memberId = person.getMemberInfoId();
            this.nickname = person.getNickname();
            this.headImg = person.getHeadImg();
        }
        this.person = person;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public EventDO getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(EventDO lastEvent) {
        this.lastEvent = lastEvent;
    }

    public Long getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(Long keepTime) {
        this.keepTime = keepTime;
    }

}

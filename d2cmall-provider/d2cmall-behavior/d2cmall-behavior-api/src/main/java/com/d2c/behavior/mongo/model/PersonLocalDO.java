package com.d2c.behavior.mongo.model;

import com.d2c.behavior.mongo.dto.LatLng;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户定位
 *
 * @author wull
 */
@Document
public class PersonLocalDO extends BaseMongoDO {

    private static final long serialVersionUID = -9004635569855548947L;
    /**
     * SessionID
     */
    @Indexed
    private String sessionId;
    /**
     * 用户ID
     */
    @Indexed
    private String personId;
    /**
     * 设备ID
     */
    @Indexed
    private String deviceId;
    /**
     * 经纬度
     */
    private LatLng point;

    public PersonLocalDO() {
    }

    public PersonLocalDO(PersonSessionDO session, LatLng point) {
        this.sessionId = session.getId();
        this.personId = session.getPersonId();
        this.deviceId = session.getDeviceId();
        this.point = point;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

}

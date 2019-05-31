package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

/**
 * @author lwz
 * @date 2016年10月29日
 */
public class AppVersionSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 开始创建时间
     */
    private Date beginTime;
    /**
     * 结束创建时间
     */
    private Date endTime;
    /**
     * 版本号
     */
    private String version;
    /**
     * IOS和Android
     */
    private String device;
    /**
     * 类型
     */
    private String type;
    /**
     * 强制升级
     */
    private Integer force;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

}

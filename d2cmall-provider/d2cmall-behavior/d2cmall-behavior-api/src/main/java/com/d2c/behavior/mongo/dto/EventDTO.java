package com.d2c.behavior.mongo.dto;

import com.d2c.common.api.dto.BaseDTO;

import java.util.Date;

/**
 * 埋点事件
 *
 * @author wull
 */
public class EventDTO extends BaseDTO {

    private static final long serialVersionUID = -2361403787015791199L;
    /**
     * 事件名称
     */
    private String event;
    /**
     * 数据对象
     */
    private Object data;
    /**
     * 触发时间
     */
    private Date gmtModified;
    /**
     * 事件备注
     */
    private String remark;

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

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}

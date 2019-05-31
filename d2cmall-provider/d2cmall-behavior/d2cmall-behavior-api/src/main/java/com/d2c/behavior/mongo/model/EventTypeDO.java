package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 埋点事件类型
 * <p>根据埋点字符串自动创建
 *
 * @author wull
 */
@Document
public class EventTypeDO extends BaseMongoDO {

    private static final long serialVersionUID = -7223788330791673547L;
    /**
     * 事件类型
     */
    @Indexed
    private String event;

    public EventTypeDO() {
    }

    public EventTypeDO(EventDO bean) {
        this.event = bean.getEvent();
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}

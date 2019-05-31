package com.d2c.behavior.mongo.service;

import com.d2c.behavior.mongo.dao.EventTypeMongoDao;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.EventTypeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 埋点事件类型
 *
 * @author wull
 */
@Component
public class EventTypeMongeService {

    @Autowired
    private EventTypeMongoDao eventTypeMongoDao;

    /**
     * 根据事件自动查询并创建事件类型
     */
    public EventTypeDO findBuildEventType(EventDO event) {
        EventTypeDO type = null;
        if (event.getEventTypeId() != null) {
            type = eventTypeMongoDao.findById(event.getEventTypeId());
        }
        if (type == null) {
            type = eventTypeMongoDao.findByEvent(event.getEvent());
        }
        if (type == null) {
            type = eventTypeMongoDao.save(new EventTypeDO(event));
        }
        event.setEventTypeId(type.getId());
        return type;
    }

}

package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.EventTypeDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class EventTypeMongoDao extends ListMongoDao<EventTypeDO> {

    /**
     * 获取事件类型
     */
    public EventTypeDO findByEvent(String event) {
        return this.findOne(new Query(Criteria.where("event").is(event)));
    }

}

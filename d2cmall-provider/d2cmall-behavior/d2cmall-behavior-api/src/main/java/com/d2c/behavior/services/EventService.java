package com.d2c.behavior.services;

import com.d2c.behavior.mongo.dto.EventDTO;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.PersonSessionDO;

import java.util.List;

public interface EventService {

    /**
     * 用户行为埋点
     */
    public EventDO event(PersonSessionDO session, EventDTO dto, EventDO last);

    public List<EventDO> event(PersonSessionDO session, List<EventDTO> list, EventDO last);

    public void updateUnShow(String id, Boolean unShow);

}

package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dto.EventDTO;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.PersonSessionDO;
import com.d2c.behavior.mongo.service.EventMongoService;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.mongodb.utils.MongoUt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户行为埋点
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class EventServiceImpl implements EventService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EventMongoService eventMongoService;
    //**********************************

    /**
     * 用户行为埋点
     *
     * @param member 登录会员
     * @param event  事件名称
     * @param json   数据对象JSON
     * @param remark 事件备注
     */
    public EventDO event(PersonSessionDO session, EventDTO dto, EventDO last) {
        return makeEvent(session, dto, last);
    }

    public List<EventDO> event(PersonSessionDO session, List<EventDTO> list, EventDO last) {
        List<EventDO> resList = new ArrayList<>();
        list.forEach(event -> {
            try {
                AssertUt.notNull(event.getEvent(), "事件名称不能为空...");
                AssertUt.notNull(event.getGmtModified(), "事件时间不能为空...");
                resList.add(event(session, event, last));
            } catch (Exception e) {
                logger.error("批量提交埋点部分失败... " + e.getMessage() + ", event:" + JsonUt.serialize(event));
            }
        });
        return resList;
    }

    public void updateUnShow(String id, Boolean unShow) {
        eventMongoService.updateUnShow(id, unShow);
    }

    /**
     * DTO转换方法
     */
    private EventDO makeEvent(PersonSessionDO session, EventDTO dto, EventDO last) {
        EventDO event = BeanUt.apply(new EventDO(session), dto);
        setTargetId(event);
        //GmtModified
        if (event.getGmtModified() == null) {
            event.setGmtModified(new Date());
        }
        //last Event and Save
        if (last != null) {
            event.setId(MongoUt.newId());
            event.setPrevId(last.getId());
            event.setPrevEvent(last.getEvent());
            last.setNextId(event.getId());
            last.setNextEvent(event.getEvent());
            eventMongoService.saveEvent(last);
        }
        return eventMongoService.saveEvent(event);
    }

    private void setTargetId(EventDO event) {
        try {
            if (event.getData() != null) {
                Object targetId = BeanUt.getValue(event.getData(), "targetId");
                if (targetId != null) {
                    event.setTargetId(targetId);
                }
            }
        } catch (Exception e) {
        }
    }

}

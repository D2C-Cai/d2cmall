package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.EventMongoDao;
import com.d2c.behavior.mongo.dto.EventStatDTO;
import com.d2c.behavior.mongo.dto.EventStatQueryDTO;
import com.d2c.behavior.mongo.dto.EventVisitorDTO;
import com.d2c.behavior.mongo.enums.EventQueryType;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.model.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 用户行为埋点
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class EventStatServiceImpl implements EventStatService {

    @Autowired
    private EventMongoDao eventMongoDao;

    /**
     * 今天，昨天，总的事件点击人数
     */
    @CacheMethod
    public EventStatDTO findUvStat(EventStatQueryDTO query) {
        EventStatDTO dto = new EventStatDTO(query.getEvent());
        dto.setUv(eventMongoDao.countByQuery(query, EventQueryType.UV));
        query.setDateTypeEnum(DateType.DAY);
        query.setDateBackNum(2);
        dto.setUvTable(eventMongoDao.findEventChart(query, EventQueryType.UV));
        dto.getUvTable().forEach(kv -> {
            if (kv.getKey() != null && kv.getKey().equals(DateUt.today())) {
                dto.setTodayUv(kv.getValue().intValue());
            }
            if (kv.getKey() != null && kv.getKey().equals(DateUt.yesterday())) {
                dto.setLastDayUv(kv.getValue().intValue());
            }
        });
        return dto;
    }

    /**
     * 查询几天内的访客数据
     */
    @CacheMethod
    public List<EventVisitorDTO> findVisitors(EventStatQueryDTO query, PageModel page) {
        return eventMongoDao.findVistiors(query, page);
    }
    //*************************************

    /**
     * 查询总图表，根据事件名称分组
     */
    public Map<String, List<KeyValue>> findEventChartMap(EventStatQueryDTO query) {
        return eventMongoDao.findEventChartMap(query);
    }

    /**
     * 查询点击次数或查询点击人数图表
     */
    public List<KeyValue> findEventChart(EventStatQueryDTO query) {
        return eventMongoDao.findEventChart(query);
    }

    /**
     * 事件点击次数PageView
     */
    public Integer findAllPv(EventStatQueryDTO query) {
        return eventMongoDao.countByQuery(query, EventQueryType.PV);
    }

    /**
     * 事件点击人数UniqueVisitor
     */
    public Integer findAllUv(EventStatQueryDTO query) {
        return eventMongoDao.countByQuery(query, EventQueryType.UV);
    }

}

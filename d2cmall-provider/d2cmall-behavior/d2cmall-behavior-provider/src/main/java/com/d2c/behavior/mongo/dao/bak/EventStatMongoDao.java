package com.d2c.behavior.mongo.dao.bak;

import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.EventStatDO;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.utils.AggrUt;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class EventStatMongoDao extends ListMongoDao<EventStatDO> {

    //统计每日用户时间线， 如 统计前30天
    private final static int STAT_EVENT_DAYS = 30;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    //**************************

    /**
     * 查询统计数据
     */
    public EventStatDO findByEvent(String event) {
        return this.findOne("event", event);
    }

    /**
     * 每日统计一次
     */
    public List<EventStatDO> buildOnDay() {
        Map<String, List<JSONObject>> pvMap = aggrDayPageViews(STAT_EVENT_DAYS);
        Map<String, List<JSONObject>> uvMap = aggrDayUniqueVisitors(STAT_EVENT_DAYS);
        Map<String, List<JSONObject>> visitorMap = aggrVisitor(7);
        Map<String, Integer> allPvMap = aggrAllPv();
        Map<String, Integer> allUvMap = aggrAllUv();
        List<EventStatDO> resList = new ArrayList<>();
        pvMap.forEach((key, pvs) -> {
            EventStatDO bean = new EventStatDO(key);
            bean.setPv(allPvMap.get(key));
            bean.setUv(allUvMap.get(key));
            bean.setDayPageViews(pvs);
            bean.setDayUniqueVisitors(uvMap.get(key));
            bean.setVisitors(visitorMap.get(key));
            setDayPuv(bean);
            resList.add(bean);
        });
        saveAll(resList);
        return resList;
    }
    //***********************************************

    /**
     * 事件总浏览数量
     */
    private Map<String, Integer> aggrAllPv() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("event").count().as("value"));
        List<JSONObject> list = aggregate(aggregation, EventDO.class, JSONObject.class);
        return ListUt.listToMap(list, "_id", "value", String.class, Integer.class);
    }

    /**
     * 事件总独立访客数量
     */
    private Map<String, Integer> aggrAllUv() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("event", "personId"),
                Aggregation.group("event").count().as("value"));
        List<JSONObject> list = aggregate(aggregation, EventDO.class, JSONObject.class);
        return ListUt.listToMap(list, "_id", "value", String.class, Integer.class);
    }

    /**
     * 查询每日页面浏览数量数据集合
     */
    private Map<String, List<JSONObject>> aggrDayPageViews(int dayBack) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("gmtModified").gte(DateUt.dayBack(dayBack))),
                Aggregation.project("event").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                Aggregation.project("event").and("gmtModified").dateAsFormattedString(AggrUt.DAY_FORMAT).as("day"),
                Aggregation.group("event", "day").count().as("value"),
                Aggregation.sort(Direction.ASC, "day"));
        List<JSONObject> list = aggregate(aggregation, EventDO.class, JSONObject.class);
        return ListUt.groupToMap(list, "event", String.class);
    }

    /**
     * 每日独立访客数量数据集合
     */
    private Map<String, List<JSONObject>> aggrDayUniqueVisitors(int dayBack) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("gmtModified").gte(DateUt.dayBack(dayBack))),
                Aggregation.project("event", "personId").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                Aggregation.project("event", "personId").and("gmtModified").dateAsFormattedString(AggrUt.DAY_FORMAT).as("day"),
                Aggregation.group("event", "day", "personId"),
                Aggregation.group("event", "day").count().as("value"),
                Aggregation.sort(Direction.ASC, "day"));
        List<JSONObject> list = aggregate(aggregation, EventDO.class, JSONObject.class);
        return ListUt.groupToMap(list, "event", String.class);
    }

    /**
     * 最近的访客
     */
    private Map<String, List<JSONObject>> aggrVisitor(int dayBack) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("gmtModified").gte(DateUt.dayBack(dayBack))),
                Aggregation.sort(Direction.DESC, "gmtModified"),
                Aggregation.group("event", "personId").last("gmtModified").as("lastDate"));
        List<JSONObject> list = aggregate(aggregation, EventDO.class, JSONObject.class);
        return ListUt.groupToMap(list, "event", String.class);
    }

    /**
     * 获取今天和昨天的PV,UV
     * <p>只查询前两条数据
     */
    private void setDayPuv(EventStatDO stat) {
        List<JSONObject> pvlist = stat.getDayPageViews();
        String today = DateUt.today();
        String yesterday = DateUt.yesterday();
        int size = pvlist.size();
        for (int i = size - 1; size - i < 2 && i >= 0; i--) {
            JSONObject pv = pvlist.get(i);
            String day = pv.getString("day");
            if (day.equals(today)) {
                stat.setTodayPv(pv.getInt("value"));
            }
            if (day.equals(yesterday)) {
                stat.setYesterdayPv(pv.getInt("value"));
            }
            JSONObject uv = stat.getDayUniqueVisitors().get(i);
            day = uv.getString("day");
            if (day.equals(today)) {
                stat.setTodayUv(uv.getInt("value"));
            }
            if (day.equals(yesterday)) {
                stat.setYesterdayUv(uv.getInt("value"));
            }
        }
    }

    @Override
    public Collection<EventStatDO> saveAll(Collection<EventStatDO> list) {
        list.forEach(bean -> {
            deleteByEvent(bean.getEvent());
            save(bean);
        });
        return list;
    }

    public void deleteByEvent(String event) {
        deleteByQuery(new Query(Criteria.where("event").is(event)));
    }

}


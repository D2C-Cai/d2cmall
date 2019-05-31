package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.dto.EventStatQueryDTO;
import com.d2c.behavior.mongo.dto.EventVisitorDTO;
import com.d2c.behavior.mongo.enums.EventEnum;
import com.d2c.behavior.mongo.enums.EventQueryType;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.core.model.KeyValue;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.build.AggrBuild;
import com.d2c.common.mongodb.page.QueryHelper;
import com.d2c.common.mongodb.utils.AggrUt;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class EventMongoDao extends ListMongoDao<EventDO> {

    /**
     * 获得session最后一条事件
     */
    public EventDO getLastBySessionId(String sessionId) {
        return findOne(QueryHelper.lastOne(new Query(Criteria.where("sessionId").is(sessionId)), false, "gmtModified"));
    }

    /**
     * 统计该事件总数
     */
    public long countByEvent(String event) {
        return count(new Query(Criteria.where("event").is(event)));
    }

    public List<Long> findVisitProductIds(String event, Long memberId, int limit) {
        AggrBuild ab = AggrBuild.build();
        ab.and("event").is(event).and("memberId").is(memberId);
        ab.and("targetId").exists(true);
        ab.add(Aggregation.group("targetId"),
                Aggregation.limit(limit));
        List<JSONObject> list = aggregate(ab.newAggregation(), EventDO.class, JSONObject.class);
        return ConvertUt.convertTypeList(ListUt.getValueExpr(list, "_id", Integer.class), Long.class);
    }

    /**
     * 查询用户事件列表
     * <p> 按天和商品合并，去重
     */
    public List<Document> aggrEventList(String event, Long memberId, PageModel page) {
        AggrBuild ab = AggrBuild.build();
        ab.and("event").is(event).and("memberId").is(memberId);
        ab.and("gmtModified").gte(DateUt.monthBack(1));
        ab.add(Aggregation.project("gmtModified", "targetId", "id").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                Aggregation.project("gmtModified", "targetId", "id").and("gmtModified").dateAsFormattedString(AggrUt.dateAsFormat(DateType.DAY)).as("day"),
                Aggregation.group("day", "targetId").last("gmtModified").as("gmtModified"),
                Aggregation.sort(Direction.DESC, "gmtModified"),
                Aggregation.skip((long) page.getStartNumber()),
                Aggregation.limit(page.getPageSize()));
        return aggregate(ab.newAggregation(), EventDO.class, Document.class);
    }

    public Integer aggrCountEventList(String event, Long memberId) {
        AggrBuild ab = AggrBuild.build();
        ab.and("event").is(event).and("memberId").is(memberId);
        ab.and("gmtModified").gte(DateUt.monthBack(1));
        ab.add(Aggregation.group("targetId"),
                Aggregation.count().as("count"));
        Document json = aggregateOne(ab.newAggregation(), EventDO.class, Document.class);
        return json != null ? json.getInteger("count") : 0;
    }
    //*************************************

    /**
     * 查询总图表，根据事件名称分组
     */
    public Map<String, List<KeyValue>> findEventChartMap(EventStatQueryDTO query) {
        return ListUt.groupToMap(findEventChartImpl(query, null, JSONObject.class), "event", String.class, KeyValue.class);
    }

    /**
     * 查询点击次数或查询点击人数图表
     */
    public List<KeyValue> findEventChart(EventStatQueryDTO query) {
        return findEventChart(query, null);
    }

    public List<KeyValue> findEventChart(EventStatQueryDTO query, EventQueryType queryType) {
        return findEventChartImpl(query, queryType, KeyValue.class);
    }

    private <T> List<T> findEventChartImpl(EventStatQueryDTO query, EventQueryType queryType, Class<T> outputType) {
        AggrBuild ab = buildOnQuery(query);
        if (queryType == null) {
            queryType = query.getQueryTypeEnum();
        }
        switch (queryType) {
            case PV:
                // 触发次数查询
                ab.add(Aggregation.project("event").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                        Aggregation.project("event").and("gmtModified").dateAsFormattedString(AggrUt.dateAsFormat(query.getDateTypeEnum())).as("key"),
                        Aggregation.group("event", "key").count().as("value"),
                        Aggregation.sort(Direction.ASC, "key"));
                break;
            default:
                // 触发人数查询
                ab.add(Aggregation.project("event", "deviceId").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                        Aggregation.project("event", "deviceId").and("gmtModified").dateAsFormattedString(AggrUt.dateAsFormat(query.getDateTypeEnum())).as("key"),
                        Aggregation.group("event", "key", "deviceId"),
                        Aggregation.group("event", "key").count().as("value"),
                        Aggregation.sort(Direction.ASC, "key"));
                break;
        }
        return aggregate(ab.newAggregation(), EventDO.class, outputType);
    }

    /**
     * 查询点击次数或查询点击人数
     */
    public Integer countByQuery(EventStatQueryDTO query) {
        return countByQuery(query, null);
    }

    public Integer countByQuery(EventStatQueryDTO query, EventQueryType queryType) {
        AggrBuild ab = buildOnQuery(query);
        if (queryType == null) {
            queryType = query.getQueryTypeEnum();
        }
        switch (queryType) {
            case PV:
                ab.add(Aggregation.group("event").count().as("value"));
                break;
            default:
                ab.add(Aggregation.group("event", "deviceId"),
                        Aggregation.group("event").count().as("value"));
                break;
        }
        JSONObject bean = aggregateOne(ab.newAggregation(), EventDO.class);
        if (bean == null) return 0;
        return bean.getInt("value");
    }

    /**
     * 修改是否用户可视
     */
    public void updateUnShow(String eventId, Boolean unShow) {
        EventDO event = findById(eventId);
        Criteria cri = Criteria.where("event").is(event.getEvent());
        cri.and("memberId").is(event.getMemberId());
        cri.and("targetId").is(event.getTargetId());
        update(new Query(cri), Update.update("unShow", unShow));
    }

    /**
     * 查询访问用户
     */
    public List<EventVisitorDTO> findVistiors(EventStatQueryDTO query, PageModel page) {
        AggrBuild ab = buildOnQuery(query);
        ab.add(Aggregation.group("deviceId").last("event").as("event")
                        .last("gmtModified").as("visitDate").last("nickname").as("nickname").last("headImg").as("headImg")
                        .last("personId").as("personId").last("memberId").as("memberId").last("data").as("data"),
                Aggregation.sort(Direction.DESC, "visitDate"),
                Aggregation.skip((long) page.getStartNumber()),
                Aggregation.limit(page.getPageSize()));
        return aggregate(ab.newAggregation(), EventDO.class, EventVisitorDTO.class);
    }

    /**
     * 各店铺访问用户数量
     * <p>查询时间段内
     */
    public List<KeyValue> countVistiors(Date startTime) {
        AggrBuild ab = AggrBuild.build();
        ab.and("event").is(EventEnum.PARTNER_VISIT.toString());
        ab.and("gmtModified").gte(startTime);
        ab.add(Aggregation.group("targetId", "deviceId"),
                Aggregation.group("targetId").count().as("value"));
        return aggregate(ab.newAggregation(), EventDO.class, KeyValue.class);
    }
    //************************ private *********************

    /**
     * 建立查询条件
     */
    private AggrBuild buildOnQuery(EventStatQueryDTO query) {
        AggrBuild ab = AggrBuild.build();
        if (query.getEvent() != null) {
            ab.and("event").is(query.getEvent());
            if (query.getFieldName() != null) {
                if ("targetId".equals(query.getFieldName())) {
                    ab.and("targetId").is(query.getFieldValue());
                } else {
                    ab.and("data." + query.getFieldName()).is(query.getFieldValue());
                }
            }
        }
        //查询时间段，默认30天
        DateType dateType = query.getDateTypeEnum();
        if (query.getDateBackNum() != null && query.getDateBackNum() >= 1) {
            query.setStartTime(DateUt.dateBack(dateType, query.getDateBackNum()));
            query.setEndTime(null);
        } else {
            //默认查询时间
            if (query.getStartTime() == null && query.getEndTime() == null) {
                query.setStartTime(DateUt.dateBack(dateType, dateType.getDefaultNum()));
                query.setEndTime(null);
            }
        }
        if (query.getStartTime() != null) {
            ab.and("gmtModified").gte(query.getStartTime());
        }
        if (query.getEndTime() != null) {
            ab.and("gmtModified").lte(query.getEndTime());
        }
        return ab;
    }

}

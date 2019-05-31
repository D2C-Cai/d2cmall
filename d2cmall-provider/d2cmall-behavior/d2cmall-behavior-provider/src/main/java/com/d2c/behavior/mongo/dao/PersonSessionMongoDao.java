package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.dto.PersonSessionDTO;
import com.d2c.behavior.mongo.enums.SessionStatus;
import com.d2c.behavior.mongo.model.PersonSessionDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.build.AggrBuild;
import com.d2c.common.mongodb.page.QueryHelper;
import com.d2c.common.mongodb.utils.AggrUt;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Session会话处理
 *
 * @author wull
 */
@Component
public class PersonSessionMongoDao extends ListMongoDao<PersonSessionDO> {

    /**
     * 获取最后一次Session会话
     */
    public PersonSessionDO findLastSessionByMemberId(Long memberId) {
        return findOne(QueryHelper.lastOne(new Query(Criteria.where("memberId").is(memberId)), false, "startTime"));
    }

    /**
     * 查询用户会话
     */
    public List<PersonSessionDO> findSessionByDeviceId(String deviceId, PageModel page) {
        return this.findPage(new Query(Criteria.where("deviceId").is(deviceId)), page, "startTime", false);
    }

    public List<PersonSessionDO> findSessionByPersonId(String personId, PageModel page) {
        return this.findPage(new Query(Criteria.where("personId").is(personId)), page, "startTime", false);
    }
    //**************************************************

    /**
     * 获取用户登录统计数据
     */
    public List<PersonSessionDTO> findPersonSessionList(List<String> personIds, PageModel page) {
        AggrBuild ab = AggrBuild.build();
        if (personIds != null && !personIds.isEmpty()) {
            ab.and("personId").in(personIds);
        } else {
            ab.and("personId").ne(null);
        }
        return aggrSessionList(ab, page, "personId");
    }

    /**
     * 获取访客登录统计数据
     */
    public List<PersonSessionDTO> findVisitorSessionList(List<String> deviceIds, PageModel page) {
        AggrBuild ab = AggrBuild.build();
        ab.and("personId").is(null);
        if (deviceIds != null && !deviceIds.isEmpty()) {
            ab.and("deviceId").in(deviceIds);
        }
        return aggrSessionList(ab, page, "deviceId");
    }

    private List<PersonSessionDTO> aggrSessionList(AggrBuild ab, PageModel page, String groupName) {
        if (page == null) page = new PageModel();
        ab.and("startTime").gte(DateUt.dayBack(30));
        ab.add(Aggregation.group(groupName).count().as("visitCount")
                        .sum("keepTime").as("allKeepTime")
                        .last("personId").as("personId")
                        .last("deviceId").as("deviceId")
                        .last("memberId").as("memberId")
                        .last("token").as("token")
                        .last("nickname").as("nickname")
                        .last("headImg").as("headImg")
                        .last("sex").as("sex")
                        .last("status").as("status")
                        .max("startTime").as("lastVisitTime"),
                Aggregation.sort(Direction.DESC, "status", "lastVisitTime"),
                Aggregation.skip((long) page.getStartNumber()),
                Aggregation.limit(page.getPageSize()));
        return aggregate(ab.newAggregation(), PersonSessionDO.class, PersonSessionDTO.class);
    }
    //******************* 用户留存数据 ********************

    /**
     * 初始用户 (首次打开用户) 留存数据查询
     */
    public List<JSONObject> aggrFirstSession(DateType dateType, Integer dateNum) {
        AggrBuild ab = AggrBuild.build();
        ab.and("gmtModified").gte(DateUt.dateBack(dateType, dateNum));
        ab.add(Aggregation.group("deviceId").first("gmtModified").as("gmtModified"),
                Aggregation.project().and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                Aggregation.project().and("gmtModified").dateAsFormattedString(AggrUt.dateAsFormat(dateType)).as("key"),
                Aggregation.group("key").count().as("value")
                        .push("$$ROOT._id").as("deviceIds")
                        .last("key").as("key"),
                Aggregation.sort(Direction.DESC, "key"));
        return aggregate(ab.newAggregation(), PersonSessionDO.class, JSONObject.class);
    }

    /**
     * 用户留存
     */
    public List<List<JSONObject>> aggrRetention(DateType dateType, Integer dateNum) {
        List<List<JSONObject>> reslist = new ArrayList<>();
        List<JSONObject> list = aggrFirstSession(dateType, dateNum);
        list.forEach(bean -> {
            AggrBuild ab = AggrBuild.build();
            ab.and("deviceId").in(bean.getJSONArray("deviceIds"));
            ab.and("gmtModified").gte(DateUt.str2date(bean.getString("key")));
            ab.add(Aggregation.project("deviceId").and("gmtModified").plus(AggrUt.GMT_CHINA_TIMEZONE).as("gmtModified"),
                    Aggregation.project("deviceId").and("gmtModified").dateAsFormattedString(AggrUt.dateAsFormat(DateType.DAY)).as("key"),
                    Aggregation.group("deviceId", "key"),
                    Aggregation.group("key").count().as("value")
                            .last("key").as("key"),
                    Aggregation.sort(Direction.DESC, "key"));
            reslist.add(aggregate(ab.newAggregation(), PersonSessionDO.class, JSONObject.class));
        });
        return reslist;
    }
    //***************************************

    public List<PersonSessionDO> findUnCloseSession(String deviceId) {
        return find(new Query(Criteria.where("deviceId").is(deviceId).and("status").is(SessionStatus.START.name())));
    }

    public PersonSessionDO findSessionClose(String sessionId) {
        return findOne(new Query(Criteria.where("_id").is(sessionId).and("status").is(SessionStatus.START.name())));
    }

}

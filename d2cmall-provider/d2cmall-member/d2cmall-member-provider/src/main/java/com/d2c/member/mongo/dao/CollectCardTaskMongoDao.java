package com.d2c.member.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.mongo.model.CollectCardTaskDO;
import com.d2c.member.mongo.model.CollectCardTaskDO.TaskType;
import com.d2c.util.date.DateUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CollectCardTaskMongoDao extends ListMongoDao<CollectCardTaskDO> {

    public CollectCardTaskDO findMine(Long memberId, String type, Boolean today) {
        if (today == null) {
            return this.findOne(new Query(Criteria.where("memberId").is(memberId).and("type").is(type)));
        } else if (today) {
            return this.findOne(new Query(Criteria.where("memberId").is(memberId).and("type").is(type).and("createDate")
                    .gte(DateUtil.getStartOfDay(new Date()))));
        } else if (!today) {
            return this.findOne(new Query(Criteria.where("memberId").is(memberId).and("type").is(type).and("createDate")
                    .lte(DateUtil.getStartOfDay(new Date()))));
        }
        return null;
    }

    public Boolean doReduceByMemberId(Long memberId) {
        Update update = new Update();
        update.set("status", 0);
        Query query = new Query(Criteria.where("memberId").is(memberId).and("type").is(TaskType.SHAREACTIVITY.name())
                .and("status").is(1));
        if (update(query, update).getModifiedCount() > 0) {
            return true;
        }
        query = new Query(
                Criteria.where("memberId").is(memberId).and("type").is(TaskType.SHARE520.name()).and("status").is(1));
        if (update(query, update).getModifiedCount() > 0) {
            return true;
        }
        return false;
    }

}

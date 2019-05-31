package com.d2c.member.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.enums.MemberTaskTypeEnum;
import com.d2c.member.mongo.enums.MemberTaskState;
import com.d2c.member.mongo.model.MemberTaskExecDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberTaskExecMongoDao extends ListMongoDao<MemberTaskExecDO> {

    public MemberTaskExecDO findOne(Long memberId, String code) {
        return findOne(new Query(Criteria.where("memberId").is(memberId).and("code").is(code)));
    }

    public List<MemberTaskExecDO> findList(Long memberId) {
        return find("memberId", memberId);
    }

    public long countAwardState(Long memberId) {
        return count(new Query(Criteria.where("memberId").is(memberId).and("state").is(MemberTaskState.AWARD_TASK.name())));
    }

    public long updateDayTask() {
        return deleteByQuery(new Query(Criteria.where("taskType").is(MemberTaskTypeEnum.DAY_TASK.name())));
    }

}

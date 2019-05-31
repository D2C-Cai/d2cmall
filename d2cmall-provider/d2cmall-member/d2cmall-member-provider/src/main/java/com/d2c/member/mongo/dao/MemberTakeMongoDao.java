package com.d2c.member.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.mongo.model.MemberTakeDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberTakeMongoDao extends ListMongoDao<MemberTakeDO> {

    public MemberTakeDO findByUnique(String uniqueMark) {
        return this.findOne(new Query(Criteria.where("uniqueMark").is(uniqueMark)));
    }

    public List<MemberTakeDO> findByMemberIdAndType(Long memberId, String type) {
        return this.find(new Query(Criteria.where("memberId").is(memberId).and("type").is(type)));
    }

}

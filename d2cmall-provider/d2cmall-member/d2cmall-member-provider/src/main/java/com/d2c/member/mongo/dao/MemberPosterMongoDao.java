package com.d2c.member.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.mongo.model.MemberPosterDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberPosterMongoDao extends ListMongoDao<MemberPosterDO> {

    public MemberPosterDO findByMemberId(String memberId) {
        return this.findOne(new Query(Criteria.where("memberId").is(memberId)));
    }

    public List<MemberPosterDO> findTops(Integer page, Integer limit) {
        return this.findPage(new Query(), page, limit, "likeCount", false);
    }

    public long countTops() {
        return this.count(new Query());
    }

    public MemberPosterDO updateLikeCount(String id, Integer count) {
        return this.updateOne(new Query(Criteria.where("_id").is(id)), Update.update("likeCount", count));
    }

    public MemberPosterDO updateDissCount(String id, Integer count) {
        return this.updateOne(new Query(Criteria.where("_id").is(id)), Update.update("dissCount", count));
    }

    public long countRanking(Integer count) {
        return this.count(new Query(Criteria.where("likeCount").gt(count)));
    }

}

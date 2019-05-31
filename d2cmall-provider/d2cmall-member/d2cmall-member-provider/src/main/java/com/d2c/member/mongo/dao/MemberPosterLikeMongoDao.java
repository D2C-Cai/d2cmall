package com.d2c.member.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.mongo.model.MemberPosterLikeDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MemberPosterLikeMongoDao extends ListMongoDao<MemberPosterLikeDO> {

    public int countByDate(String memberId, String postId, Date startDate, Date endDate) {
        return (int) this.count(new Query(Criteria.where("memberId").is(memberId).and("postId").is(postId)
                .and("createDate").lte(endDate).gte(startDate)));
    }

    public List<MemberPosterLikeDO> findByPostId(String postId, Integer page, Integer limit) {
        return this.findPage(new Query(Criteria.where("postId").is(postId)), page, limit, "createDate", false);
    }

    public long countByPostId(String postId) {
        return this.count(new Query(Criteria.where("postId").is(postId)));
    }

}

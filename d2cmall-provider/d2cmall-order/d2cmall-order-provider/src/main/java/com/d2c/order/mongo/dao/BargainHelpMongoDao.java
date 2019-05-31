package com.d2c.order.mongo.dao;

import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.order.mongo.model.BargainHelpDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BargainHelpMongoDao extends ListMongoDao<BargainHelpDO> {

    public int countByUnionIdAndDate(String unionId, Date begainDate, Date endDate) {
        return (int) this.count(
                new Query(Criteria.where("helperUnionId").is(unionId).and("helpDate").gte(begainDate).lte(endDate)));
    }

    public int countByUnionIdAndBarginId(String unionId, String bargainId) {
        return (int) this.count(new Query(Criteria.where("helperUnionId").is(unionId).and("bargainId").is(bargainId)));
    }

    public List<BargainHelpDO> findByBargainId(String bargainId, Integer page, Integer limit) {
        return this.findPage(new Query(Criteria.where("bargainId").is(bargainId)), page, limit, "helpDate", false);
    }

    public long countByBargainId(String bargainId) {
        return this.count(new Query(Criteria.where("bargainId").is(bargainId)));
    }

}

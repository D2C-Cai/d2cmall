package com.d2c.similar.mongo.dao;

import com.d2c.common.mongodb.base.BaseMongoDao;
import com.d2c.common.mongodb.enums.ReportStatus;
import com.d2c.similar.mongo.model.SimilarReportDO;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class SimilarReportMongoDao extends BaseMongoDao<SimilarReportDO> {

    public SimilarReportDO findLastReport(Integer schemeId) {
        Query query = new Query(Criteria.where("schemeId").is(schemeId)
                .and("status").is(ReportStatus.END.name()));
        query.with(new Sort(Direction.DESC, "gmtCreate"));
        return findOne(query);
    }

    public SimilarReportDO findLastUnDoneReport(Integer schemeId) {
        Query query = new Query(Criteria.where("schemeId").is(schemeId)
                .and("status").is(ReportStatus.START.name()));
        query.with(new Sort(Direction.DESC, "gmtCreate"));
        return findOne(query);
    }

}

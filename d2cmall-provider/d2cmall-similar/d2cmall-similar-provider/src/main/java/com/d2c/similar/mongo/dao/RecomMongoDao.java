package com.d2c.similar.mongo.dao;

import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.similar.mongo.model.RecomDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecomMongoDao extends ListMongoDao<RecomDO> {

    public List<RecomDO> findTopRecom(MongoQuery query, int limit) {
        return findQueryPage(null, query, 1, limit, "score", false);
    }

}

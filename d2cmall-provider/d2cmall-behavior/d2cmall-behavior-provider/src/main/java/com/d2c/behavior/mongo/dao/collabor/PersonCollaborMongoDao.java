package com.d2c.behavior.mongo.dao.collabor;

import com.d2c.behavior.mongo.model.collabor.PersonCollaborDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * 用户协同过滤
 *
 * @author wull
 */
@Component
public class PersonCollaborMongoDao extends ListMongoDao<PersonCollaborDO> {

    public PersonCollaborDO findCollabor(Long memberId, Long productId) {
        return findOne(new Query(Criteria.where("memberId").is(memberId).and("productId").is(productId)));
    }

}

package com.d2c.behavior.mongo.dao.depict;

import com.d2c.behavior.mongo.model.depict.DepictTagDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户标签画像
 *
 * @author wull
 */
@Component
public class DepictTagMongoDao extends ListMongoDao<DepictTagDO> {

    /**
     * 根据depictId获取
     */
    public List<DepictTagDO> findByDepictId(String depictId) {
        return this.find(new Query(Criteria.where("depictId").is(depictId)));
    }

}

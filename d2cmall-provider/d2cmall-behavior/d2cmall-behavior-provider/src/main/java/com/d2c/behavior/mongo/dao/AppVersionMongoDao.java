package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.AppVersionDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class AppVersionMongoDao extends ListMongoDao<AppVersionDO> {

    /**
     * 查询设备，不存在则新建
     */
    public AppVersionDO findOneByVersion(String appTerminal, String appVersion) {
        return findOne(new Query(Criteria.where("appTerminal").is(appTerminal).and("appVersion").is(appVersion)));
    }

}

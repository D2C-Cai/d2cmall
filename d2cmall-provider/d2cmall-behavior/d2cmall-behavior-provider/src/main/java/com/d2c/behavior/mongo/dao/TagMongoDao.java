package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.TagDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户标签定义
 *
 * @author wull
 */
@Component
public class TagMongoDao extends ListMongoDao<TagDO> {

    public List<TagDO> findByTypeId(String typeId) {
        return find("typeId", typeId);
    }

}

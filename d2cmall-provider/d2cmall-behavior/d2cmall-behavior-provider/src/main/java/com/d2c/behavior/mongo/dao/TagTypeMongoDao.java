package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.TagTypeDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户标签类型定义
 *
 * @author wull
 */
@Component
public class TagTypeMongoDao extends ListMongoDao<TagTypeDO> {

    public List<TagTypeDO> findByInputClz(String inputClz) {
        return find("inputClz", inputClz);
    }

}

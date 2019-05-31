package com.d2c.behavior.mongo.dao.depict;

import com.d2c.behavior.mongo.model.depict.DepictDO;
import com.d2c.common.api.json.JsonBean;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.build.AggrBuild;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户画像规则
 *
 * @author wull
 */
@Component
public class DepictMongoDao extends ListMongoDao<DepictDO> {

    /**
     * 查询画像列表
     */
    public List<DepictDO> findPageByTagId(String tagId, PageModel pager) {
        pager.addSort("tagSum");
        return this.findPage(new Query(Criteria.where("tagId").is(tagId)), pager);
    }

    public List<DepictDO> findPageByTypeId(String typeId, PageModel pager) {
        pager.addSort("tagSum");
        return this.findPage(new Query(Criteria.where("typeId").is(typeId)), pager);
    }

    /**
     * 根据加载类名获取
     */
    public DepictDO findOneByTypeId(String personId, String typeId) {
        return this.findOne(new Query(Criteria.where("personId").is(personId).and("typeId").is(typeId)));
    }

    public JsonBean countDepict() {
        AggrBuild ab = AggrBuild.build();
        ab.add(Aggregation.group().sum("count").as("count"));
        return aggregateOne(ab.newAggregation(), DepictDO.class, JsonBean.class);
    }

}

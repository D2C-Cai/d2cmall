package com.d2c.behavior.mongo.query;

import com.d2c.behavior.mongo.dto.EventStatQueryDTO;
import com.d2c.common.api.query.BaseQueryResolver;
import com.d2c.common.base.utils.AssertUt;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * 搜索DAO支撑
 *
 * @author wull
 */
public class EventQueryResolver extends BaseQueryResolver {

    /**
     * 按事件查询，并根据data中的字段条件查询
     */
    public static Criteria buildEventQuery(EventStatQueryDTO query) {
        AssertUt.notEmpty(query.getEvent(), "查询事件不能为空");
        Criteria cri = Criteria.where("event").is(query.getEvent());
        if (query.getFieldName() != null) {
            cri.and("data." + query.getFieldName()).is(query.getFieldValue());
        }
        return cri;
    }

}

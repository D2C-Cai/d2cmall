package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.ProductHistoryDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductHistoryMongoDao extends ListMongoDao<ProductHistoryDO> {

    /**
     * 查询当天该商品是否已存在浏览记录
     */
    public ProductHistoryDO findHistoryOnDay(Long memberId, Long productId, String eventDay) {
        Criteria cri = Criteria.where("memberId").is(memberId)
                .and("productId").is(productId)
                .and("eventDay").is(eventDay);
        return findOne(new Query(cri));
    }

    /**
     * 查询商品浏览历史列表
     */
    public List<ProductHistoryDO> findHistoryDays(Long memberId, PageModel page, Integer days) {
        Criteria cri = Criteria.where("memberId").is(memberId)
                .and("eventTime").gte(DateUt.dayBack(days));
        return findPage(new Query(cri), page);
    }

    /**
     * 查询商品浏览历史数量
     */
    public long countHistoryDays(Long memberId, Integer days) {
        Criteria cri = Criteria.where("memberId").is(memberId)
                .and("eventTime").gte(DateUt.dayBack(days));
        return count(new Query(cri));
    }

    /**
     * 删除该用户所有浏览历史记录
     */
    public long deleteAllHistory(Long memberId) {
        return deleteByQuery(new Query(Criteria.where("memberId").is(memberId)));
    }

}

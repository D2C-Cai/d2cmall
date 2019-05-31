package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.EventMongoDao;
import com.d2c.behavior.mongo.enums.EventEnum;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.core.model.KeyValue;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户行为搜索
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class EventQueryServiceImpl implements EventQueryService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EventMongoDao eventMongoDao;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 查询用户浏览商品ID列表
     */
    @CacheMethod
    public List<Long> findVisitProductIds(Long memberId, Integer limit) {
        String event = EventEnum.PRODUCT.toString();
        return eventMongoDao.findVisitProductIds(event, memberId, limit);
    }

    /**
     * 我的足迹 - 用户浏览历史记录
     */
    @CacheMethod
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PageResult findProductVisit(Long memberId, PageModel page) {
        String event = EventEnum.PRODUCT.toString();
        Integer count = eventMongoDao.aggrCountEventList(event, memberId);
        List<Document> events = eventMongoDao.aggrEventList(event, memberId, page);
        List<Object> list = new ArrayList<>();
        events.forEach(bean -> {
            String targetId = ConvertUt.convertType(bean.get("targetId"), String.class);
            SearcherProduct product = productSearcherQueryService.findById(targetId);
            if (product != null) {
                com.alibaba.fastjson.JSONObject json = product.toJson();
                json.put("eventTime", bean.getDate("gmtModified").getTime());
                json.put("eventDay", bean.getString("day"));
                list.add(json);
            }
        });
        return new PageResult<>(page, list, count);
    }

    @CacheMethod
    public Integer countProductEvent(Long memberId) {
        String event = EventEnum.PRODUCT.toString();
        return eventMongoDao.aggrCountEventList(event, memberId);
    }

    /**
     * 各店铺访问用户数量
     * <p>
     * 查询时间段内
     */
    @CacheMethod
    public List<KeyValue> countVistiors(Date startTime) {
        return eventMongoDao.countVistiors(startTime);
    }

}

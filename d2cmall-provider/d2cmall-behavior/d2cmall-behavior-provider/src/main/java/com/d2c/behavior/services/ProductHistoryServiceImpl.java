package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.ProductHistoryMongoDao;
import com.d2c.behavior.mongo.model.ProductHistoryDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户商品浏览记录
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class ProductHistoryServiceImpl implements ProductHistoryService {

    private final static Integer HISTORY_DAYS = 30;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductHistoryMongoDao productHistoryMongoDao;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 查询商品浏览历史列表
     */
    public PageResult<?> findHistoryDays(Long memberId, PageModel page) {
        long count = productHistoryMongoDao.countHistoryDays(memberId, HISTORY_DAYS);
        List<ProductHistoryDO> historys = productHistoryMongoDao.findHistoryDays(memberId, page, HISTORY_DAYS);
        List<Object> list = new ArrayList<>();
        historys.forEach(bean -> {
            String pid = ConvertUt.convertType(bean.getProductId(), String.class);
            SearcherProduct product = productSearcherQueryService.findById(pid);
            if (product != null) {
                com.alibaba.fastjson.JSONObject json = product.toJson();
                json.put("eventTime", bean.getEventTime().getTime());
                json.put("eventDay", bean.getEventDay());
                json.put("historyId", bean.getId());
                list.add(json);
            }
        });
        return new PageResult<>(page, list, count);
    }

    /**
     * 查询商品浏览历史数量
     */
    public long countHistoryDays(Long memberId) {
        return productHistoryMongoDao.countHistoryDays(memberId, HISTORY_DAYS);
    }

    /**
     * 删除商品浏览数据
     */
    public long deleteHistoryById(String historyId) {
        return productHistoryMongoDao.deleteById(historyId);
    }

    /**
     * 删除商品浏览历史
     */
    public long deleteAllHistory(Long memberId) {
        return productHistoryMongoDao.deleteAllHistory(memberId);
    }

    /**
     * 新增商品浏览记录
     */
    public ProductHistoryDO addProductHistory(Long memberId, Long productId) {
        ProductHistoryDO bean = new ProductHistoryDO(memberId, productId);
        ProductHistoryDO last = productHistoryMongoDao.findHistoryOnDay(memberId, productId, bean.getEventDay());
        if (last != null)
            return last;
        return productHistoryMongoDao.save(bean);
    }

}

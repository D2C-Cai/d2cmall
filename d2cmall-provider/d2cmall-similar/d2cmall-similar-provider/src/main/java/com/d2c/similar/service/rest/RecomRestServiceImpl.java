package com.d2c.similar.service.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.similar.mongo.dao.RecomMongoDao;
import com.d2c.similar.recom.RecomHandler;
import com.d2c.similar.service.RecomRuleService;
import com.d2c.similar.service.RecomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class RecomRestServiceImpl implements RecomRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private RecomRuleService recomRuleService;
    @Autowired
    private RecomService recomService;
    @Autowired
    private RecomMongoDao recomMongoDao;
    @Autowired
    private RecomHandler recomHandler;

    /**
     * 重建规则
     */
    @Override
    public Object resetRules() {
        return recomRuleService.rebuildRules();
    }

    /**
     * 清除运营推荐
     */
    @Override
    public Object cleanOperRecom() {
        logger.info("===== 清除运营操作并重建数据开始... ===== ");
        recomService.cleanOperAndRecomAll();
        logger.info("===== 清除运营操作并重建数据完成... ===== ");
        return recomMongoDao.findTopRecom(null, 20);
    }

    /**
     * 数据重建
     */
    @Override
    public Object reset() {
        logger.info("===== 推荐商品数据重建开始... ===== ");
        recomHandler.cleanAll();
        recomService.buildAllRecomInJob();
        logger.info("===== 推荐商品数据重建完成... ===== ");
        return recomMongoDao.findTopRecom(null, 20);
    }

}

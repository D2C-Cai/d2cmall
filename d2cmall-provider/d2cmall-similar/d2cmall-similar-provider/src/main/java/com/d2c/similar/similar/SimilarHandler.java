package com.d2c.similar.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.similar.constant.SimilarConst;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.helper.SimilarHelper;
import com.d2c.similar.mongo.dao.SimilarMongoDao;
import com.d2c.similar.mongo.dao.SimilarReportMongoDao;
import com.d2c.similar.mongo.model.SimilarDO;
import com.d2c.similar.mongo.model.SimilarReportDO;
import com.d2c.similar.service.SimilarRuleService;
import com.d2c.similar.service.SimilarSchemeService;
import com.d2c.similar.similar.resolver.SimilarResolver;
import com.d2c.similar.similar.resolver.SimilarResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

@Component
public class SimilarHandler {

    private final static Logger logger = LoggerFactory.getLogger(SimilarHandler.class);
    @Autowired
    private SimilarSchemeService similarSchemeService;
    @Autowired
    private SimilarRuleService similarRuleService;
    @Autowired
    private SimilarMongoDao similarMongoDao;
    @Autowired
    private SimilarReportMongoDao similarReportMongoDao;
    @Autowired
    private SimilarResolverFactory similarResolverFactory;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 根据方案执行相似度对比
     */
    @Async
    public void similarJob() {
        List<SimilarSchemeDO> schemes = similarSchemeService.findAll();
        for (SimilarSchemeDO scheme : schemes) {
            similarJob(scheme);
        }
    }

    public SimilarReportDO similarJob(SimilarSchemeDO scheme) {
        SimilarJob similarJob = new SimilarJob(this, productSearcherQueryService);
        return similarJob.similar(scheme);
    }
    // ********************************************

    public int similarOne(SimilarSchemeDO scheme, Object bean) {
        logger.debug("开始计算商品" + findId(bean) + "相似度...");
        SimilarResolver rsv = buildResolver(scheme.getId());
        List<? extends Object> tgList = productSearcherQueryService.findSimilarTargets(scheme.getCategoryId().longValue(), 1, scheme.getMaxSize());
        int res = similarOneImpl(rsv, scheme, bean, tgList, false);
        return res;
    }

    public int similarOneImpl(SimilarResolver rsv, SimilarSchemeDO scheme, Object bean, List<? extends Object> tgList, boolean isPool) {
        TreeMap<Double, SimilarDO> treeMap = new TreeMap<>();
        Object beanId = findId(bean);
        for (Object target : tgList) {
            if (beanId.equals(findId(target))) {
                continue;
            }
            SimilarDO sb = rsv.similar(scheme, bean, target);
            treeMap.put(sb.getProb(), sb);
            if (treeMap.size() > SimilarConst.SAVE_TOP_PROB_NUMBER) {
                treeMap.pollFirstEntry();
            }
        }
        save(beanId, treeMap.values(), isPool);
        return treeMap.size();
    }

    private void save(final Object beanId, final Collection<SimilarDO> list, boolean isPool) {
        if (isPool) {
            MyExecutors.limilPool().execute(new Runnable() {
                @Override
                public void run() {
                    saveImpl(beanId, list);
                }
            });
        } else {
            saveImpl(beanId, list);
        }
    }

    private void saveImpl(Object beanId, Collection<SimilarDO> list) {
        similarMongoDao.removeByBeanId(beanId);
        similarMongoDao.saveAll(list);
    }
    //******************* update **********************

    /**
     * 反向更新相似商品数据
     */
    public int updateSimilarOneImpl(SimilarResolver rsv, SimilarSchemeDO scheme, Object bean) {
        Object beanId = findId(bean);
        List<SimilarDO> list = similarMongoDao.findByTargetId(beanId);
        for (SimilarDO item : list) {
            SimilarDO sm = rsv.similar(scheme, item.getBean(), bean);
            MyExecutors.limilPool().execute(new Runnable() {
                @Override
                public void run() {
                    similarMongoDao.save(sm);
                }
            });
        }
        return list.size();
    }

    /**
     * 删除下架不被查询商品
     */
    public int deleteSimilarByBeanId(final Object beanId) {
        long count = 0;
        count += similarMongoDao.removeByBeanId(beanId);
        count += similarMongoDao.removeByTargetId(beanId);
        return (int) count;
    }
    //************************************************

    public int doAfterJob(Integer schemeId) {
        return similarSchemeService.updateSchemeHasExce(schemeId, true);
    }

    public SimilarReportDO findLastReport(Integer schemeId) {
        return similarReportMongoDao.findLastReport(schemeId);
    }

    /**
     * 清空重置
     */
    public void cleanAll() {
        similarMongoDao.cleanAll();
        similarReportMongoDao.cleanAll();
    }

    private Object findId(Object bean) {
        return SimilarHelper.findId(bean);
    }

    /**
     * 相似度解析器
     */
    public SimilarResolver buildResolver(Integer schemeId) {
        List<SimilarRuleDO> rules = similarRuleService.findRulesBySchemeId(schemeId);
        return similarResolverFactory.initResolver(rules);
    }

}

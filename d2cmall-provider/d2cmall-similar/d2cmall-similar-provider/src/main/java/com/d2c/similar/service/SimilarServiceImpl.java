package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.base.utils.RandomUt;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.TopCategoryService;
import com.d2c.similar.constant.SimilarConst;
import com.d2c.similar.dto.SimilarDTO;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.mongo.dao.SimilarMongoDao;
import com.d2c.similar.mongo.model.SimilarDO;
import com.d2c.similar.similar.SimilarHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wull
 */
@Service(protocol = "dubbo")
public class SimilarServiceImpl implements SimilarService {

    @Reference
    private TopCategoryService topCategoryService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private SimilarSchemeService similarSchemeService;
    @Autowired
    private SimilarMongoDao similarMongoDao;
    @Autowired
    private SimilarHandler similarHandler;

    /**
     * 相似度计算任务
     */
    public void buildSimilarJob() {
        similarHandler.similarJob();
    }

    /**
     * 根据商品ID列表查询TOP综合相似商品
     */
    public List<Object> findTopRandomByIds(List<?> beanIds, int limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanIds(beanIds, SimilarConst.SELECT_LIMIT_MULTIPLY * limit);
        return randomDistinctTarget(list, limit, beanIds);
    }

    /**
     * 查询有活动的相似推荐高的商品
     */
    public List<Object> findTopPromotionByIds(List<?> beanIds, int limit) {
        List<SimilarDO> list = similarMongoDao.findHasPromotionByBeanIds(beanIds,
                SimilarConst.SELECT_LIMIT_MULTIPLY * limit);
        return randomDistinctTarget(list, limit, beanIds);
    }

    /**
     * 每个品类商品相似度推荐最高的商品
     */
    public Map<Long, Object> findTopOneCategoryByIds(List<?> beanIds, int limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanIds(beanIds, SimilarConst.SELECT_LIMIT_MULTIPLY * limit);
        Map<Long, Object> resMap = new LinkedHashMap<>();
        for (SimilarDO bean : list) {
            if (bean.getTarget() instanceof SearcherProduct) {
                SearcherProduct product = (SearcherProduct) bean.getTarget();
                Long key = product.getProductCategoryId();
                if (!resMap.containsKey(key)) {
                    resMap.put(key, product);
                }
            }
            if (resMap.size() >= limit)
                break;
        }
        return resMap;
    }

    public Map<String, List<Object>> findCategoryJsonByIds(List<?> beanIds, int limit, int groupSize) {
        List<SimilarDO> list = similarMongoDao.findByBeanIds(beanIds, 20 * limit);
        Map<String, List<Object>> resMap = new LinkedHashMap<>();
        for (SimilarDO bean : list) {
            if (bean.getTarget() instanceof SearcherProduct) {
                SearcherProduct product = (SearcherProduct) bean.getTarget();
                String key = product.getCategoryName();
                List<Object> ls = resMap.get(key);
                if (ls == null) {
                    if (resMap.size() >= limit) {
                        continue;
                    }
                    ls = new ArrayList<>();
                    resMap.put(key, ls);
                }
                if (ls.size() >= groupSize) {
                    continue;
                }
                ls.add(product.toJson());
            }
        }
        return resMap;
    }

    /**
     * 查询品类商品相似度推荐最高的商品
     */
    public List<Object> findTopCategoryById(List<?> beanIds, Long categoryId, int limit) {
        List<SimilarDO> list = similarMongoDao.findTopCategoryByIds(beanIds, categoryId, limit);
        return ListUt.distinct(convertTarget(list));
    }

    /**
     * 相似度随机{limit}条从查询总数最高商品
     */
    public List<Object> findTopRandom(Object beanId, Integer limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanId(beanId, limit * SimilarConst.SELECT_LIMIT_MULTIPLY);
        return convertTarget(RandomUt.randomList(list, limit));
    }

    /**
     * 根据商品查询相似对象
     */
    public List<SimilarDO> findRebuild(List<?> beanIds, Integer limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanIds(beanIds, limit);
        if (list.isEmpty() && beanIds != null) {
            beanIds.forEach(id -> {
                rebuildSimilar(id);
            });
            list = similarMongoDao.findByBeanIds(beanIds, limit);
        }
        return list;
    }

    public List<SimilarDO> findRebuild(Object beanId, Integer limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanId(beanId, limit);
        if (list.isEmpty()) {
            int res = rebuildSimilar(beanId);
            if (res > 0) {
                list = similarMongoDao.findByBeanId(beanId, limit);
            }
        }
        return list;
    }

    /**
     * 根据商品查询相似对象
     */
    public List<SimilarDTO> findRebuildByBeanId(Object beanId, MongoQuery query, Integer page, Integer limit) {
        List<SimilarDTO> list = findByBeanId(beanId, query, page, limit);
        if (list.isEmpty()) {
            int res = rebuildSimilar(beanId);
            if (res > 0) {
                list = findByBeanId(beanId, query, page, limit);
            }
        }
        return list;
    }

    public long countByBeanId(Object beanId, MongoQuery query) {
        return similarMongoDao.countByBeanId(beanId, query);
    }

    public List<SimilarDTO> findByBeanId(Object beanId, MongoQuery query, Integer page, Integer limit) {
        List<SimilarDO> list = similarMongoDao.findByBeanId(beanId, query, page, limit);
        return ConvertUt.convertList(list, SimilarDTO.class);
    }

    /**
     * 根据商品查询相似对象
     */
    public int rebuildSimilar(Object beanId) {
        AssertUt.notNull(beanId);
        SearcherProduct product = productSearcherQueryService.findById(beanId.toString());
        if (product == null)
            return 0;
        SimilarSchemeDO scheme = similarSchemeService.findSchemeByCategoryId(product.getTopCategoryId());
        if (scheme == null)
            return 0;
        return similarHandler.similarOne(scheme, product);
    }
    // ********************* private *****************

    private List<Object> convertTarget(List<SimilarDO> list) {
        List<Object> resList = new ArrayList<>();
        for (SimilarDO bean : list) {
            resList.add(bean.getTarget());
        }
        return resList;
    }

    /**
     * 对target商品随机去重
     */
    private List<Object> randomDistinctTarget(List<SimilarDO> list, int limit, List<?> excludeIds) {
        List<Object> beans = convertTarget(list);
        beans = ListUt.distinct(beans);
        return RandomUt.randomList(beans, limit);
    }

    @Override
    public List<Object> findByTargetIds(List<Long> ids, Integer limit) {
        List<SimilarDO> list = similarMongoDao.findByTargetIds(ids, limit);
        return ListUt.distinct(convertTarget(list));
    }

}

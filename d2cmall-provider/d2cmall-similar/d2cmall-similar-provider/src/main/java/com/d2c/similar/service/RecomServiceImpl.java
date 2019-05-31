package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.service.ProductService;
import com.d2c.similar.dto.RecomDTO;
import com.d2c.similar.mongo.dao.RecomMongoDao;
import com.d2c.similar.mongo.model.RecomDO;
import com.d2c.similar.recom.RecomHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品推荐表
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class RecomServiceImpl implements RecomService {

    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Reference
    private ProductService productService;
    @Autowired
    private RecomMongoDao recomMongoDao;
    @Autowired
    private RecomHandler recomHandler;

    /**
     * 每个品类商品推荐最高的商品
     */
    public Map<Long, Object> findTopOneCategory(Integer limit) {
        Map<Long, Object> map = new LinkedHashMap<>();
        List<SearcherProduct> list = productSearcherQueryService.findTopRecom(200);
        for (SearcherProduct bean : list) {
            Long key = bean.getProductCategoryId();
            if (!map.containsKey(key)) {
                map.put(key, bean);
            }
            if (map.size() >= limit) break;
        }
        return map;
    }

    /**
     * 查询商品推荐明细，如果为空重建
     */
    public RecomDTO findBuildRecomByProductId(Long productId) {
        RecomDTO bean = convert(recomMongoDao.findById(productId));
        if (bean == null) {
            bean = buildRecomByProductId(productId, null);
        }
        return bean;
    }

    public RecomDTO findRecomByProductId(Long productId) {
        return convert(recomMongoDao.findById(productId));
    }

    /**
     * 查询 TOP {limit} 推荐值商品
     */
    public List<RecomDTO> findTopRecom(MongoQuery query, int limit) {
        return convert(recomMongoDao.findTopRecom(query, limit));
    }

    /**
     * 定时任务: 所有商品推荐值计算
     */
    public void buildAllRecomInJob() {
        recomHandler.recommendAll();
    }

    /**
     * 清除运营推荐
     */
    public void cleanOperAndRecomAll() {
        recomHandler.cleanOperAndRecomAll();
    }

    /**
     * 根据商品Id，计算商品推荐值并保存
     */
    public RecomDTO buildRecomByProductId(Long productId, Boolean operRecom) {
        if (productId == null)
            return null;
        SearcherProduct product = productSearcherQueryService.findById(productId.toString());
        RecomDO bean = recomHandler.recommend(product, operRecom, false);
        return convert(bean);
    }
    // ********************* private **********************

    private RecomDTO convert(RecomDO bean) {
        return ConvertUt.convertBean(bean, RecomDTO.class);
    }

    private List<RecomDTO> convert(List<RecomDO> list) {
        return ConvertUt.convertList(list, RecomDTO.class);
    }

}

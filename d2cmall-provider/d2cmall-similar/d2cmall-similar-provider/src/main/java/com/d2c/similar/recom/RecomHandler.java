package com.d2c.similar.recom;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.service.ProductService;
import com.d2c.similar.dto.ProductRecomDTO;
import com.d2c.similar.entity.RecomRuleDO;
import com.d2c.similar.mongo.dao.RecomMongoDao;
import com.d2c.similar.mongo.dao.RecomReportMongoDao;
import com.d2c.similar.mongo.model.RecomDO;
import com.d2c.similar.recom.resolver.RecomResolver;
import com.d2c.similar.recom.resolver.RecomResolverFactory;
import com.d2c.similar.service.RecomRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class RecomHandler {

    private final static ExecutorService pools = MyExecutors.newLimit(20);
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Reference
    private ProductService productService;
    @Autowired
    private RecomMongoDao recomMongoDao;
    @Autowired
    private RecomReportMongoDao recomReportMongoDao;
    @Autowired
    private RecomRuleService recomRuleService;
    @Autowired
    private RecomResolverFactory recomResolverFactory;
    private ThreadLocal<RecomResolver> rsvLocal = new ThreadLocal<>();
    private ThreadLocal<RecomReportProxy> reportLocal = new ThreadLocal<>();

    /**
     * 所有商品推荐值重新计算
     */
    public void recommendAll() {
        recommendAll(false);
    }

    /**
     * 清除运营操作并重建数据
     */
    public void cleanOperAndRecomAll() {
        recommendAll(true);
    }

    private void recommendAll(boolean isCleanOper) {
        PageBucket<SearcherProduct> bucket = buildPageBucket(null);
        RecomReportProxy report = getReportProxy();
        report.start();
        bucket.forEach(bean -> {
            try {
                recommend(bean, isCleanOper ? false : null, true);
                report.addOne();
            } catch (Exception e) {
                report.error(e.getMessage(), e);
            }
        });
        report.end();
    }

    /**
     * 商品推荐计算
     */
    public RecomDO recommend(SearcherProduct product, Boolean operRecom, Boolean isPool) {
        RecomDO recom = buildRecom(product, operRecom);
        buildResolver().resolver(recom);
        if (isPool != null && isPool) {
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    save(product, recom, operRecom);
                }
            });
        } else {
            save(product, recom, operRecom);
        }
        return recom;
    }
    //********************* private **********************

    private void save(SearcherProduct product, RecomDO recom, Boolean operRecom) {
        recomMongoDao.save(recom);
        productService.updateRecomById(recom.getProductId(), recom.getScore(), operRecom);
        //更新EhSearch索引
        productSearcherService.updateRecom(product.getId(), recom.getScore(), operRecom);
    }

    /**
     * 根据推荐规则rule，构造推荐数据
     */
    private RecomDO buildRecom(final SearcherProduct product, Boolean operRecom) {
        RecomDO recom = new RecomDO(product.getProductId(), product);
        ProductRecomDTO bean = recom.getData();
        bean.setRecentlySales(product.getRecentlySales());
        bean.setSales(product.getSales());
        bean.setComments(product.getComments());
        bean.setPromotion(product.getPromotionId() != null);
        if (product.getCreateDate() != null) {
            bean.setNewDays((int) DateUt.fromDays(product.getCreateDate()));
        }
        //运营推荐
        if (operRecom != null) {
            bean.setOperRecom(operRecom);
            product.setOperRecom(operRecom ? 1 : 0);
        } else {
            bean.setOperRecom(product.getIsOperRecom());
        }
        return recom;
    }

    /**
     * 商品分页装桶
     */
    private PageBucket<SearcherProduct> buildPageBucket(Integer maxSize) {
        return new PageBucket<SearcherProduct>(maxSize) {
            @Override
            public List<SearcherProduct> nextList(int page, int pageSize) {
                return productSearcherQueryService.findByPage(page, pageSize);
            }
        };
    }

    /**
     * 创建推荐解析器
     */
    private RecomResolver buildResolver() {
        RecomResolver resolver = rsvLocal.get();
        if (resolver == null) {
            //查询并创建规则
            List<RecomRuleDO> rules = recomRuleService.findAllRules();
            resolver = recomResolverFactory.initResolver(rules);
            rsvLocal.set(resolver);
        }
        return resolver;
    }

    /**
     * 获得报表代理器
     */
    private RecomReportProxy getReportProxy() {
        RecomReportProxy proxy = reportLocal.get();
        if (proxy == null) {
            proxy = new RecomReportProxy();
            reportLocal.set(proxy);
        }
        return proxy;
    }

    /**
     * 清空重置
     */
    public void cleanAll() {
        recomMongoDao.cleanAll();
        recomReportMongoDao.cleanAll();
    }

}
 
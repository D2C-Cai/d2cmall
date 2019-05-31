package com.d2c.similar.service.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.product.model.TopCategory;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.TopCategoryService;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.mongo.dao.SimilarReportMongoDao;
import com.d2c.similar.service.SimilarSchemeService;
import com.d2c.similar.similar.SimilarHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class SimilarRestServiceImpl implements SimilarRestService {

    @Reference
    private TopCategoryService topCategoryService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private SimilarSchemeService similarSchemeService;
    @Autowired
    private SimilarReportMongoDao similarReportMongoDao;
    @Autowired
    private SimilarHandler similarHandler;

    @Override
    public Object init() {
        similarSchemeService.clean();
        List<TopCategory> list = topCategoryService.findAll(1);
        for (TopCategory top : list) {
            similarSchemeService.createScheme(convertScheme(top));
        }
        return list;
    }

    private SimilarSchemeDO convertScheme(TopCategory top) {
        SimilarSchemeDO bean = new SimilarSchemeDO();
        bean.setId(top.getId().intValue());
        bean.setSchemeName(top.getName());
        bean.setCategoryId(top.getId().intValue());
        bean.setMaxSize(10000);
        return bean;
    }

    @Override
    public Object test() {
        return null;
    }

    @Override
    public Object start() {
        similarHandler.similarJob();
        return similarReportMongoDao.findPage(null, new PageModel(1, 20, false, "lastDate"));
    }

    @Override
    public Object reset() {
        for (SimilarSchemeDO scheme : similarSchemeService.findAll()) {
            similarSchemeService.updateSchemeHasExce(scheme.getId(), false);
        }
        similarHandler.cleanAll();
        similarHandler.similarJob();
        return similarReportMongoDao.findAll();
    }

}

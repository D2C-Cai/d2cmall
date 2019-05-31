package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.FlashPromotionMapper;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.query.FlashPromotionSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("flashPromotionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FlashPromotionServiceImpl extends ListServiceImpl<FlashPromotion> implements FlashPromotionService {

    @Autowired
    private FlashPromotionMapper flashPromotionMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    @Cacheable(value = "flash_promotion", key = "'flash_promotion_'+#id", unless = "#result == null")
    public FlashPromotion findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public PageResult<FlashPromotion> findBySearcher(FlashPromotionSearcher searcher, PageModel page) {
        PageResult<FlashPromotion> pager = new PageResult<>(page);
        int totalCount = flashPromotionMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<FlashPromotion> list = flashPromotionMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public FlashPromotion insert(FlashPromotion flashPromotion) {
        return this.save(flashPromotion);
    }

    @Override
    @CacheEvict(value = "flash_promotion", key = "'flash_promotion_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Long id, String operator) {
        int success = flashPromotionMapper.deleteById(id, operator);
        if (success > 0) {
            ProductSearcher searcher = new ProductSearcher();
            searcher.setFlashPromotionId(id);
            PageModel page = new PageModel();
            int numberPage = 1;
            page.setP(numberPage);
            page.setPageSize(500);
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            PageResult<SearcherProduct> pager = new PageResult<>();
            do {
                pager = productSearcherQueryService.search(searcherBean, page);
                for (SearcherProduct product : pager.getList()) {
                    productService.deleteFlashPromotion(product.getId());
                }
                page.setPageNumber(page.getPageNumber() + 1);
            } while (pager.isNext());
        }
        return success;
    }

    @Override
    @CacheEvict(value = "flash_promotion", key = "'flash_promotion_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateStatus(Long id, Integer status, String operator) {
        FlashPromotion flashPromotion = this.findOneById(id);
        flashPromotion.setStatus(status);
        int success = flashPromotionMapper.updateStatus(id, status, operator);
        if (success > 0) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    refreshProductSearcher(flashPromotion);
                }
            });
            executor.shutdown();
        }
        return success;
    }

    @Override
    @CacheEvict(value = "flash_promotion", key = "'flash_promotion_'+#flashPromotion.id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(FlashPromotion flashPromotion) {
        int success = flashPromotionMapper.updatePromotion(flashPromotion);
        if (success > 0) {
            final FlashPromotion newPromotion = this.findOneById(flashPromotion.getId());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    refreshProductSearcher(newPromotion);
                }
            });
            executor.shutdown();
        }
        return success;
    }

    /**
     * 活动变动更新商品
     *
     * @param flashPromotionId
     */
    protected void refreshProductSearcher(FlashPromotion flashPromotion) {
        ProductSearcher searcher = new ProductSearcher();
        searcher.setFlashPromotionId(flashPromotion.getId());
        PageModel page = new PageModel();
        int numberPage = 1;
        page.setP(numberPage);
        page.setPageSize(500);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> pager = new PageResult<>();
        do {
            pager = productSearcherQueryService.search(searcherBean, page);
            for (SearcherProduct product : pager.getList()) {
                productModuleSearchService.updateFlashPromotion(product.getId(), flashPromotion);
            }
            page.setPageNumber(page.getPageNumber() + 1);
        } while (pager.isNext());
    }

    @Override
    @CacheEvict(value = "flash_promotion", key = "'flash_promotion_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSort(Long id, Integer sort) {
        return flashPromotionMapper.updateSort(id, sort);
    }

    @Override
    public List<FlashPromotion> findSession(Date beginDate, Date endDate, Integer promotionScope, String channel) {
        return flashPromotionMapper.findSession(promotionScope, beginDate, endDate, channel);
    }

    @Override
    public List<FlashPromotion> findBySessionAndDate(String session, Date startDate, Integer promotionScope,
                                                     String channel) {
        return flashPromotionMapper.findBySessionAndDate(session, startDate, promotionScope, channel);
    }

    @Override
    public FlashPromotion findByStartDateAndScope(Date startDate, Integer promotionScope, String channel) {
        return flashPromotionMapper.findByStartDateAndScope(startDate, promotionScope, channel);
    }

    @Override
    public List<FlashPromotion> findNoEndOrderByScore(String channel) {
        return flashPromotionMapper.findNoEndOrderByScore(channel);
    }

}

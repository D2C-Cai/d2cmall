package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.PromotionMapper;
import com.d2c.product.dto.PromotionDto;
import com.d2c.product.dto.PromotionHelpDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.query.PromotionSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("promotionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PromotionServiceImpl extends ListServiceImpl<Promotion> implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private BrandService brandService;

    @Cacheable(value = "promotion", key = "'promotion_'+#id", unless = "#result == null")
    public Promotion findById(Long id) {
        Promotion promotion = super.findOneById(id);
        return promotion;
    }

    @Cacheable(value = "promotion_simple", key = "'promotion_simple_'+#id", unless = "#result == null")
    public Promotion findSimpleById(Long id) {
        Promotion promotion = promotionMapper.findSimpleById(id);
        return promotion;
    }

    @Override
    @Cacheable(value = "tag_promotion_list_", key = "'tag_promotion_'+#tagId", unless = "#result == null")
    public PageResult<Promotion> findByTagId(Long tagId, Boolean enable, PageModel page) {
        PageResult<Promotion> pager = new PageResult<Promotion>(page);
        int totalCount = promotionMapper.countByTagId(tagId, enable);
        List<Promotion> list = new ArrayList<Promotion>();
        if (totalCount > 0) {
            list = promotionMapper.findByTagId(tagId, enable, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    public PageResult<Promotion> findBySearcher(PromotionSearcher searcher, PageModel page) {
        PageResult<Promotion> pager = new PageResult<Promotion>(page);
        int totalCount = promotionMapper.countBySearcher(searcher);
        List<Promotion> list = new ArrayList<Promotion>();
        if (totalCount > 0) {
            list = promotionMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(PromotionSearcher searcher) {
        return this.promotionMapper.countBySearcher(searcher);
    }

    @Override
    public List<HelpDTO> findHelpDtosBySearcher(PromotionSearcher promotionSearcher, PageModel promotionPage) {
        int totalCount = promotionMapper.countBySearcher(promotionSearcher);
        List<Promotion> list = new ArrayList<Promotion>();
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        if (totalCount > 0) {
            list = promotionMapper.findBySearcher(promotionSearcher, promotionPage);
            for (Promotion p : list) {
                PromotionHelpDto dto = new PromotionHelpDto();
                dto.setId(p.getId());
                dto.setName(p.getName());
                dto.setStartTime(DateUtil.minute2str(p.getStartTime()));
                dto.setEndTime(DateUtil.minute2str(p.getEndTime()));
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Promotion insert(Promotion promotion) {
        promotion = this.save(promotion);
        if (promotion.getTiming() == 1) {
            this.timingPromotionMQ(promotion, 1, promotion.getStartTime());
        }
        return promotion;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "promotion", key = "'promotion_'+#promotion.id"),
            @CacheEvict(value = "promotion_simple", key = "'promotion_simple_'+#promotion.id")})
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int update(Promotion promotion) {
        Promotion old = this.findOneById(promotion.getId());
        if (old.getEnable()) {
            throw new BusinessException("活动已上架，修改不成功，请先下架后再处理！");
        }
        if (promotion.getEnable() != null && promotion.getEnable()) {
            throw new BusinessException("活动已上架，修改不成功，请先下架后再处理！");
        }
        int result = this.updateNotNull(promotion);
        if (promotion.getTiming() == 1) {
            if (!(old.getStartTime().equals(promotion.getStartTime()))) {
                this.timingPromotionMQ(promotion, 1, promotion.getStartTime());
            }
        }
        return result;
    }

    private void timingPromotionMQ(Promotion promotion, int mark, Date date) {
        Date now = new Date();
        if (promotion.getStartTime().after(now)) {
            long interval = (date.getTime() - now.getTime()) / 1000;
            Map<String, Object> map = new HashMap<>();
            map.put("id", promotion.getId());
            map.put("mark", mark);
            map.put("date", date.getTime());
            MqEnum.TIMING_PROMOTION.send(map, interval);
        }
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "promotion", key = "'promotion_'+#id"),
            @CacheEvict(value = "promotion_simple", key = "'promotion_simple_'+#id")})
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Long id) {
        return this.deleteById(id);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "promotion", key = "'promotion_'+#id"),
            @CacheEvict(value = "promotion_simple", key = "'promotion_simple_'+#id")})
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doTiming(Long id, int timing) {
        Promotion promotion = this.findOneById(id);
        Date now = new Date();
        if (promotion.getEnable()) {
            if (promotion.getStartTime().before(now)) {
                throw new BusinessException("自动上架时间异常！");
            }
        } else {
            if (promotion.getEndTime().before(now)) {
                throw new BusinessException("自动下架时间异常！");
            }
        }
        if (timing == 1) {
            this.timingPromotionMQ(promotion, 1, promotion.getStartTime());
        }
        return promotionMapper.doTiming(id, timing);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "promotion", key = "'promotion_'+#id"),
            @CacheEvict(value = "promotion_simple", key = "'promotion_simple_'+#id")})
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doMark(boolean enable, Long id) {
        Promotion promotion = this.findOneById(id);
        promotion.setEnable(enable);
        int result = 0;
        result = promotionMapper.doMark(enable, id);
        if (result > 0) {
            if (enable) {
                this.timingPromotionMQ(promotion, 0, promotion.getEndTime());
            }
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    refreshProductSearch(promotion);
                }
            });
            executor.shutdown();
        }
        return result;
    }

    protected void refreshProductSearch(Promotion promotion) {
        ProductSearcher searcher = new ProductSearcher();
        if (promotion.getPromotionScope() == 0) {
            searcher.setPromotionId(promotion.getId());
        } else if (promotion.getPromotionScope() == 1) {
            searcher.setOrderPromotionId(promotion.getId());
        }
        PageModel page = new PageModel();
        int numberPage = 1;
        page.setP(numberPage);
        page.setPageSize(500);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> pager = new PageResult<>();
        do {
            pager = productSearcherQueryService.search(searcherBean, page);
            for (SearcherProduct product : pager.getList()) {
                productModuleSearchService.updatePromotion(product.getId(), promotion);
            }
            page.setPageNumber(page.getPageNumber() + 1);
        } while (pager.isNext());
    }

    @Override
    public PageResult<PromotionDto> findDtoBySearcher(PromotionSearcher promotionSearcher, PageModel promotionPage) {
        PageResult<PromotionDto> pager = new PageResult<PromotionDto>(promotionPage);
        int totalCount = promotionMapper.countBySearcher(promotionSearcher);
        List<PromotionDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            List<Promotion> list = promotionMapper.findBySearcher(promotionSearcher, promotionPage);
            for (Promotion promotion : list) {
                PromotionDto dto = new PromotionDto();
                BeanUtils.copyProperties(promotion, dto);
                if (promotion.getBrandId() != null) {
                    Brand brand = brandService.findById(promotion.getBrandId());
                    if (brand != null) {
                        dto.setBrandName(brand.getName());
                    }
                }
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

}

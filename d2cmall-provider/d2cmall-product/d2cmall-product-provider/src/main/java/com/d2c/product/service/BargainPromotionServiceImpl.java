package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BargainPromotionMapper;
import com.d2c.product.dto.BargainPromotionDto;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.Product;
import com.d2c.product.query.BargainPromotionSearcher;
import com.d2c.product.search.model.SearcherBargainPromotion;
import com.d2c.product.search.service.BargainPromotionSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 砍价活动
 *
 * @author wwn
 */
@Service("bargainPromotionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BargainPromotionServiceImpl extends ListServiceImpl<BargainPromotion> implements BargainPromotionService {

    @Autowired
    private BargainPromotionMapper bargainPromotionMapper;
    @Autowired
    private ProductService productService;
    @Reference
    private BargainPromotionSearcherService bargainPromotionSearcherService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BargainPromotion insert(BargainPromotion bargainPromotion) {
        bargainPromotion = this.save(bargainPromotion);
        if (bargainPromotion.getId() != null) {
            SearcherBargainPromotion bargain = new SearcherBargainPromotion();
            BeanUtils.copyProperties(bargainPromotion, bargain);
            bargainPromotionSearcherService.insert(bargain);
        }
        return bargainPromotion;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BargainPromotion bargainPromotion, String opertor) {
        BargainPromotion promotion = this.findById(bargainPromotion.getId());
        int success = this.updateNotNull(bargainPromotion);
        if (success > 0) {
            if (!bargainPromotion.getMinPrice().equals(promotion.getMinPrice())
                    || !bargainPromotion.getEndDate().equals(promotion.getEndDate())) {
                logger.info(promotion.getName() + "原始最低价：" + promotion.getMinPrice() + "；改后价："
                        + bargainPromotion.getMinPrice() + "初始时间" + promotion.getEndDate() + "改后时间："
                        + bargainPromotion.getEndDate() + "操作人：" + opertor);
            }
            SearcherBargainPromotion bargain = new SearcherBargainPromotion();
            BeanUtils.copyProperties(bargainPromotion, bargain);
            bargainPromotionSearcherService.rebuild(bargain);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        int success = bargainPromotionMapper.updateSort(id, sort);
        if (success > 0) {
            bargainPromotionSearcherService.updateSort(id, sort);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMark(Long id, Integer mark, String operator) {
        int success = bargainPromotionMapper.updateMark(id, mark, operator);
        if (success > 0) {
            bargainPromotionSearcherService.updateMark(id, mark);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id, String operator) {
        int success = bargainPromotionMapper.updateMark(id, -1, operator);
        if (success > 0) {
            bargainPromotionSearcherService.remove(id);
        }
        return success;
    }

    @Override
    public PageResult<BargainPromotionDto> findBySearcher(BargainPromotionSearcher searcher, PageModel page) {
        PageResult<BargainPromotionDto> pager = new PageResult<>(page);
        int totalCount = bargainPromotionMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<BargainPromotion> list = bargainPromotionMapper.findBySearcher(searcher, page);
            List<BargainPromotionDto> dtos = new ArrayList<>();
            for (BargainPromotion promotion : list) {
                BargainPromotionDto dto = new BargainPromotionDto();
                BeanUtils.copyProperties(promotion, dto);
                Product product = productService.findById(promotion.getProductId());
                dto.setProduct(product);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public BargainPromotion findById(Long id) {
        return findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCount(Long id) {
        int success = bargainPromotionMapper.addCount(id);
        if (success > 0) {
            bargainPromotionSearcherService.updateCount(id);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRemind(Long memberId, String loginCode, SearcherBargainPromotion bargainPromotion) {
        Integer status = redisHandler.get("remind_" + memberId + "_" + bargainPromotion.getId());
        if (status != null && status == 1) {
            throw new BusinessException("您已经设置提醒！");
        }
        redisHandler.setInHours("remind_" + memberId + "_" + bargainPromotion.getId(), 1, 24);
        Long seconds = bargainPromotion.getBeginDate().getTime() - 30 * 60 * 1000 - new Date().getTime();
        if (seconds > 0) {
            this.bargainRemindMQ(memberId, bargainPromotion.getId(), loginCode, seconds / 1000);
        } else {
            throw new BusinessException("活动即将开始！");
        }
        return 1;
    }

    private void bargainRemindMQ(Long memberId, Long promotionId, String loginCode, Long seconds) {
        Map<String, Object> mq = new HashMap<>();
        mq.put("memberId", memberId);
        mq.put("promotionId", promotionId);
        mq.put("mobile", loginCode);
        MqEnum.BARGAIN_REMIND.send(mq, seconds);
    }

    @Override
    public void deleteRemind(Long memberId, Long promotionId) {
        redisHandler.delete("remind_" + memberId + "_" + promotionId);
    }

}

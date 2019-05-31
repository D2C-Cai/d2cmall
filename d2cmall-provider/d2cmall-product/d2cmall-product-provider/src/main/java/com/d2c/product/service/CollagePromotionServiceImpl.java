package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.CollagePromotionMapper;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.query.CollagePromotionSearcher;
import com.d2c.product.search.model.SearcherCollagePromotion;
import com.d2c.product.search.service.CollagePromotionSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "collagePromotionService")
@Transactional(rollbackFor = Exception.class)
public class CollagePromotionServiceImpl extends ListServiceImpl<CollagePromotion> implements CollagePromotionService {

    @Autowired
    private CollagePromotionMapper collagePromotionMapper;
    @Reference
    private CollagePromotionSearcherService collagePromotionSearcherService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    @Override
    @Cacheable(value = "collage_promotion", key = "'collage_promotion_'+#id", unless = "#result==null")
    public CollagePromotion findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<CollagePromotion> findBySearch(CollagePromotionSearcher searcher, PageModel page) {
        PageResult<CollagePromotion> pager = new PageResult<>(page);
        int totalCount = collagePromotionMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<CollagePromotion> list = collagePromotionMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollagePromotion insert(CollagePromotion collagePromotion) {
        collagePromotion = this.save(collagePromotion);
        if (collagePromotion.getId() != null && collagePromotion.getId() > 0) {
            SearcherCollagePromotion searcherCollagePromotion = new SearcherCollagePromotion();
            BeanUtils.copyProperties(collagePromotion, searcherCollagePromotion);
            collagePromotionSearcherService.insert(searcherCollagePromotion);
        }
        return collagePromotion;
    }

    @Override
    @CacheEvict(value = "collage_promotion", key = "'collage_promotion_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Long id, String operator) {
        int success = collagePromotionMapper.updateStatus(id, -1, operator);
        if (success > 0) {
            collagePromotionSearcherService.updateStatus(id, -1);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "collage_promotion", key = "'collage_promotion_'+#collagePromotion.id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int update(CollagePromotion collagePromotion) {
        // 有关商品的不能更新
        CollagePromotion oldCollagepromotion = this.findOneById(collagePromotion.getId());
        collagePromotion.setProductId(oldCollagepromotion.getProductId());
        collagePromotion.setProductImage(oldCollagepromotion.getProductImage());
        collagePromotion.setProductName(oldCollagepromotion.getProductName());
        if (oldCollagepromotion.getBeginDate().before(new Date())
                && !(oldCollagepromotion.getBeginDate().equals(collagePromotion.getBeginDate())
                || !(oldCollagepromotion.getEndDate().equals(collagePromotion.getEndDate())
                || !(oldCollagepromotion.getExpireTime().equals(collagePromotion.getExpireTime()))))) {
            throw new BusinessException("活动已开始不能修改时间！");
        }
        int success = updateNotNull(collagePromotion);
        if (success > 0) {
            SearcherCollagePromotion searcher = new SearcherCollagePromotion();
            BeanUtils.copyProperties(collagePromotion, searcher);
            collagePromotionSearcherService.rebuild(searcher);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "collage_promotion", key = "'collage_promotion_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateSort(Long id, Integer sort) {
        int success = collagePromotionMapper.updateSort(id, sort);
        if (success > 0) {
            collagePromotionSearcherService.updateSort(id, sort);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "collage_promotion", key = "'collage_promotion_'+#promotionId")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateCurrentCount(Long promotionId, int num) {
        return collagePromotionMapper.updateCurrentCount(promotionId, num);
    }

    @Override
    @CacheEvict(value = "collage_promotion", key = "'collage_promotion_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doMark(Long id, Integer status, String operator) {
        CollagePromotion collagePromotion = this.findOneById(id);
        int success = collagePromotionMapper.updateStatus(id, status, operator);
        if (success > 0) {
            collagePromotionSearcherService.updateStatus(id, status);
            if (status == 1) {
                productService.updateCollagePromotion(collagePromotion.getProductId(), collagePromotion.getId(),
                        operator);
            } else {
                productService.deleteCollagePromotion(collagePromotion.getProductId());
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doRemind(Long memberId, Long promotionId, String mobile) {
        CollagePromotion promotion = this.findById(promotionId);
        if (promotion == null || promotion.getStatus() != 1 || promotion.getEndDate().before(new Date())) {
            throw new BusinessException("该活动已经结束，看看其他拼团活动吧！");
        }
        Integer status = redisHandler.get("collage_remind_" + memberId + "_" + promotionId);
        if (status != null && status == 1) {
            throw new BusinessException("您已经设置提醒！");
        }
        redisHandler.set("collage_remind_" + memberId + "_" + promotionId, 1);
        // 设置时距离开始小于5分钟就不用提醒了
        Long seconds = promotion.getBeginDate().getTime() - 5 * 60 * 1000 - new Date().getTime();
        if (seconds > 0) {
            this.collageRemindMQ(mobile, memberId, promotion.getId(), seconds / 1000);
        } else {
            throw new BusinessException("活动即将开始！");
        }
        return 1;
    }

    private void collageRemindMQ(String mobile, Long memberId, Long promotionId, Long seconds) {
        Map<String, Object> mq = new HashMap<>();
        mq.put("memberId", memberId);
        mq.put("promotionId", promotionId);
        mq.put("mobile", mobile);
        MqEnum.COLLAGE_REMIND.send(mq, seconds);
    }

    @Override
    public void deleteRemind(Long memberId, Long promotionId) {
        redisHandler.delete("collage_remind_" + memberId + "_" + promotionId);
    }

}

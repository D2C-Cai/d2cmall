package com.d2c.product.service;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.PromotionTagRelationMapper;
import com.d2c.product.dto.PromotionTagRelationDto;
import com.d2c.product.model.PromotionTagRelation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("promotionTagRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PromotionTagRelationServiceImpl extends ListServiceImpl<PromotionTagRelation>
        implements PromotionTagRelationService {

    @Autowired
    private PromotionTagRelationMapper promotionTagRelationMapper;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private RedisHandler<String, Object> redisHandler;

    @Override
    @CacheEvict(value = "tag_promotion_list_", key = "'tag_promotion_'+#relation.tagId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PromotionTagRelation insert(PromotionTagRelation relation) {
        return this.save(relation);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByPromotionId(Long promotionId) {
        List<PromotionTagRelation> list = promotionTagRelationMapper.findByPromotionId(promotionId);
        int success = promotionTagRelationMapper.deleteByPromotionId(promotionId);
        for (PromotionTagRelation relation : list) {
            this.clearTagCache(relation.getTagId());
        }
        return success;
    }

    @Override
    @CacheEvict(value = "tag_promotion_list_", key = "'tag_promotion_'+#tagId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByPromotionIdAndTagId(Long promotionId, Long tagId) {
        return promotionTagRelationMapper.deleteByPromotionIdAndTagId(promotionId, tagId);
    }

    @Override
    public List<PromotionTagRelation> findByPromotionId(Long promotionId) {
        return promotionTagRelationMapper.findByPromotionId(promotionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, int sort) {
        int success = promotionTagRelationMapper.updateSort(id, sort);
        PromotionTagRelation relation = this.findOneById(id);
        this.clearTagCache(relation.getTagId());
        return success;
    }

    @Override
    public PageResult<PromotionTagRelationDto> findByTagId(Long tagId, PageModel page) {
        PageResult<PromotionTagRelationDto> pager = new PageResult<PromotionTagRelationDto>(page);
        Integer totalCount = promotionTagRelationMapper.countByTagId(tagId);
        List<PromotionTagRelationDto> list = new ArrayList<>();
        if (totalCount > 0) {
            List<PromotionTagRelation> items = promotionTagRelationMapper.findByTagId(tagId, page);
            for (PromotionTagRelation item : items) {
                PromotionTagRelationDto dto = new PromotionTagRelationDto();
                BeanUtils.copyProperties(item, dto);
                dto.setPromotion(promotionService.findById(item.getPromotionId()));
                list.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    private void clearTagCache(Long tagId) {
        redisHandler.delete("tag_promotion_" + tagId);
    }

}

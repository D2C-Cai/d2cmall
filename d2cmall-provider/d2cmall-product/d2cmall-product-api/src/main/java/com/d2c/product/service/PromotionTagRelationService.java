package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.PromotionTagRelationDto;
import com.d2c.product.model.PromotionTagRelation;

import java.util.List;

public interface PromotionTagRelationService {

    /**
     * 新增
     *
     * @param relation
     * @return
     */
    PromotionTagRelation insert(PromotionTagRelation relation);

    /**
     * 根据promotionId删除
     *
     * @param promotionId
     * @return
     */
    int deleteByPromotionId(Long promotionId);

    /**
     * 根据promotionId和tagId删除
     *
     * @param promotionId
     * @param tagId
     * @return
     */
    int deleteByPromotionIdAndTagId(Long promotionId, Long tagId);

    /**
     * 根据promotionId查询
     *
     * @param promotionId
     * @return
     */
    List<PromotionTagRelation> findByPromotionId(Long promotionId);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, int sort);

    /**
     * 后台关联表查询
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<PromotionTagRelationDto> findByTagId(Long tagId, PageModel page);

}

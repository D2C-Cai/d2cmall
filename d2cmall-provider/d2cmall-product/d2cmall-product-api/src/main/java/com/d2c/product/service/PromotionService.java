package com.d2c.product.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.PromotionDto;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.PromotionSearcher;

import java.util.List;

public interface PromotionService {

    /**
     * 通过促销活动的id，查找出具体的促销活动
     *
     * @param id
     * @return
     */
    Promotion findById(Long id);

    /**
     * 通过促销活动的id，查找出具体的促销活动
     *
     * @param id
     * @return
     */
    Promotion findSimpleById(Long id);

    /**
     * 通过tagId，查找出具体的促销活动
     *
     * @param tagId
     * @param enable
     * @param page
     * @return
     */
    PageResult<Promotion> findByTagId(Long tagId, Boolean enable, PageModel page);

    /**
     * 通过促销活动的查询条件和分页条件，查找出促销活动的分页数据
     *
     * @param promotionSearcher 查询条件
     * @param promotionPage     分页条件
     * @return
     */
    PageResult<Promotion> findBySearcher(PromotionSearcher promotionSearcher, PageModel promotionPage);

    /**
     * 通过促销活动的查询条件和分页条件，查找出数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(PromotionSearcher searcher);

    /**
     * 通过查询条件和分页条件，查找出简单的促销活动的集合
     *
     * @param promotionSearcher
     * @param promotionPage
     * @return
     */
    List<HelpDTO> findHelpDtosBySearcher(PromotionSearcher promotionSearcher, PageModel promotionPage);

    /**
     * 增加一条促销活动
     *
     * @param promotion
     * @return
     */
    Promotion insert(Promotion promotion);

    /**
     * 逻辑删除促销活动的数据，将促销活动设置成删除状态，并且清空缓存
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 更新促销活动数据，并且刷新缓存
     *
     * @param promotion
     * @return
     */
    int update(Promotion promotion);

    /**
     * 将促销活动设置成定时状态
     *
     * @param id
     * @param status 1:定时上架，0:非
     * @return
     */
    int doTiming(Long id, int status);

    /**
     * 更新促销活动的上下架状态，并且刷新缓存
     *
     * @param mark
     * @param promotionId
     * @return
     */
    int doMark(boolean mark, Long promotionId);

    /**
     * 查询dto
     *
     * @param promotionSearcher
     * @param promotionPage
     * @return
     */
    PageResult<PromotionDto> findDtoBySearcher(PromotionSearcher promotionSearcher, PageModel promotionPage);

}

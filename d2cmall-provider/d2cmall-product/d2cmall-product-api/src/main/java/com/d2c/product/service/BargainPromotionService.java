package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.BargainPromotionDto;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.query.BargainPromotionSearcher;
import com.d2c.product.search.model.SearcherBargainPromotion;

/**
 * 砍价活动
 *
 * @author Administrator
 */
public interface BargainPromotionService {

    /**
     * 插入活动
     *
     * @param bargainPromotion
     * @return
     */
    BargainPromotion insert(BargainPromotion bargainPromotion);

    /**
     * 更新活动
     *
     * @param bargainPromotion
     * @return
     */
    int update(BargainPromotion bargainPromotion, String opertor);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateMark(Long id, Integer status, String operator);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int delete(Long id, String operator);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BargainPromotionDto> findBySearcher(BargainPromotionSearcher searcher, PageModel page);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    BargainPromotion findById(Long id);

    /**
     * 更新参团人数
     *
     * @param id
     * @return
     */
    int updateCount(Long id);

    /**
     * 提醒
     *
     * @param memberId
     * @param loginCode
     * @param bargainPromotion
     * @return
     */
    int doRemind(Long memberId, String loginCode, SearcherBargainPromotion bargainPromotion);

    /**
     * 删除提醒
     *
     * @param memberId
     * @param promotionId
     */
    void deleteRemind(Long memberId, Long promotionId);

}

package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.query.FlashPromotionSearcher;

import java.util.Date;
import java.util.List;

public interface FlashPromotionService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    FlashPromotion findById(Long id);

    /**
     * 查询该场次下的活动
     *
     * @param session
     * @param startDate
     * @return
     */
    List<FlashPromotion> findBySessionAndDate(String session, Date startDate, Integer promotionScope, String channel);

    /**
     * 分页查找
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<FlashPromotion> findBySearcher(FlashPromotionSearcher searcher, PageModel page);

    /**
     * 插入
     *
     * @param flashProduct
     * @return
     */
    FlashPromotion insert(FlashPromotion flashProduct);

    /**
     * 删除
     *
     * @param id
     * @param operator
     * @return
     */
    int deleteById(Long id, String operator);

    /**
     * 上下架
     *
     * @param id
     * @param status
     * @param username
     * @return
     */
    int updateStatus(Long id, Integer status, String username);

    /**
     * 更新
     *
     * @param flashPromotion
     * @return
     */
    int update(FlashPromotion flashPromotion);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 查询所有场次
     *
     * @param beginDate
     * @param endDate
     * @param promotionScope
     * @return
     */
    List<FlashPromotion> findSession(Date beginDate, Date endDate, Integer promotionScope, String channel);

    /**
     * @param startDate
     * @param promotionScope
     * @return
     */
    FlashPromotion findByStartDateAndScope(Date startDate, Integer promotionScope, String channel);

    /**
     * 查找未结束的，按类型排序
     *
     * @return
     */
    List<FlashPromotion> findNoEndOrderByScore(String channel);

}

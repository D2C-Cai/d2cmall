package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.query.CollagePromotionSearcher;

public interface CollagePromotionService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    CollagePromotion findById(Long id);

    /**
     * 分页查询，除去删除的
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CollagePromotion> findBySearch(CollagePromotionSearcher searcher, PageModel page);

    /**
     * 新增
     *
     * @param collagePromotion
     * @return
     */
    CollagePromotion insert(CollagePromotion collagePromotion);

    /**
     * 删除
     *
     * @param id
     * @param operator
     * @return
     */
    int delete(Long id, String operator);

    /**
     * 更新
     *
     * @param collagePromotion
     * @return
     */
    int update(CollagePromotion collagePromotion);

    /**
     * 排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 修改活动开团数 条件：
     * <li>活动中；上架的； 当前开团人数+num<=限制开团数
     *
     * @param id
     * @param num
     * @return
     */
    int updateCurrentCount(Long id, int num);

    /**
     * 上下架
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int doMark(Long id, Integer status, String operator);

    /**
     * 设置提醒
     *
     * @param memberId
     * @param promotionId
     * @param mobile
     * @return
     */
    int doRemind(Long memberId, Long promotionId, String mobile);

    /**
     * 删除提醒
     *
     * @param memberId
     * @param promotionId
     * @return
     */
    void deleteRemind(Long memberId, Long promotionId);

}

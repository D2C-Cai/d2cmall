package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherBargainPromotion;
import com.d2c.product.search.query.BargainPromotionSearchBean;

public interface BargainPromotionSearcherService {

    public static final String TYPE_BARGAIN = "typebargain";

    /**
     * 插入
     *
     * @param bargainPromotion
     * @return
     */
    int insert(SearcherBargainPromotion bargainPromotion);

    /**
     * 重建
     *
     * @param bargainPromotion
     * @return
     */
    int rebuild(SearcherBargainPromotion bargainPromotion);

    /**
     * 查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherBargainPromotion> search(BargainPromotionSearchBean searcher, PageModel page);

    /**
     * 删除所有
     *
     * @return
     */
    int removeAll();

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int remove(Long id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SearcherBargainPromotion findById(Long id);

    /**
     * 添加砍价人数
     *
     * @param id
     * @return
     */
    int updateCount(Long id);

    /**
     * 更新排名
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 更新上下架状态
     *
     * @param id
     * @param mark
     * @return
     */
    int updateMark(Long id, Integer mark);

}

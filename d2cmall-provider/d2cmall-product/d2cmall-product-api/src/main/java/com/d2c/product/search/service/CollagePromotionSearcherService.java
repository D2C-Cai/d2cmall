package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherCollagePromotion;
import com.d2c.product.search.query.CollagePromotionSearcherBean;

public interface CollagePromotionSearcherService {

    public static final String TYPE_COLLAGE = "typecollages";

    /**
     * 插入
     *
     * @param collagePromotion
     * @return
     */
    int insert(SearcherCollagePromotion collagePromotion);

    /**
     * 重建
     *
     * @param collagePromotion
     * @return
     */
    int rebuild(SearcherCollagePromotion collagePromotion);

    /**
     * 全部删除
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
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 查找单个
     *
     * @param id
     * @return
     */
    SearcherCollagePromotion findById(Long id);

    /**
     * 列表查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherCollagePromotion> search(CollagePromotionSearcherBean searcher, PageModel page);

}

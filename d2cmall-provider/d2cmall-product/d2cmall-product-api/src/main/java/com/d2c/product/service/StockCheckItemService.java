package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.StockCheckItem;
import com.d2c.product.query.StockCheckItemSearcher;

public interface StockCheckItemService {

    /**
     * 初始化库存
     *
     * @param storeCode
     * @param checkId
     * @return
     */
    int doInit(String storeCode, Long checkId);

    /**
     * 新增
     *
     * @param stockCheckItem
     * @return
     */
    StockCheckItem insert(StockCheckItem stockCheckItem);

    /**
     * 更新
     *
     * @param stockCheckItem
     * @return
     */
    int update(StockCheckItem stockCheckItem);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据checkId删除
     *
     * @param checkId
     * @return
     */
    int deleteByCheckId(Long checkId);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    StockCheckItem findById(Long id);

    /**
     * 查询
     *
     * @param checkId
     * @param barCode
     * @return
     */
    StockCheckItem findOne(Long checkId, String barCode);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<StockCheckItem> findBySearch(StockCheckItemSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(StockCheckItemSearcher searcher);

}

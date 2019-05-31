package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.StockCheck;
import com.d2c.product.query.StockCheckSearcher;

public interface StockCheckService {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    StockCheck findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<StockCheck> findBySearch(StockCheckSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(StockCheckSearcher searcher);

    /**
     * 新增
     *
     * @param stockCheck
     * @return
     */
    StockCheck insert(StockCheck stockCheck);

    /**
     * 更改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更改备注
     *
     * @param id
     * @param memo
     * @return
     */
    int updateMemo(Long id, String memo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

}

package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.WithdrawCashSearcher;

public interface WithdrawCashService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    WithdrawCash findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<WithdrawCash> findBySearch(WithdrawCashSearcher searcher, PageModel page);

    /**
     * 提现创建
     *
     * @param id
     * @return
     */
    WithdrawCash insert(WithdrawCash cash);

    /**
     * 提现确认
     *
     * @param id
     * @return
     */
    int doConfirm(Long id);

    /**
     * 提现关闭
     *
     * @param id
     * @return
     */
    int doClose(Long id);

    /**
     * 提现成功
     *
     * @param drawCash
     * @return
     */
    int doSuccess(WithdrawCash drawCash);

}

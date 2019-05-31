package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.AwardSession;
import com.d2c.product.query.AwardSessionSearcher;

import java.util.List;

public interface AwardSessionService {

    /**
     * 新增
     *
     * @param awardSession
     * @return
     */
    AwardSession insert(AwardSession awardSession);

    /**
     * 修改
     *
     * @param awardSession
     * @return
     */
    int update(AwardSession awardSession);

    /**
     * 用ID查询
     *
     * @param id
     * @return
     */
    AwardSession findById(Long id);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AwardSession> findBySearcher(AwardSessionSearcher searcher, PageModel page);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 按抽奖权益查询
     *
     * @param over
     * @param lotterySource
     * @return
     */
    List<AwardSession> findByLotterySource(Integer over, String lotterySource);

}

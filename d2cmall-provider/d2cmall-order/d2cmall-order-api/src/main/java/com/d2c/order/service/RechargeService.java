package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.RechargeDto;
import com.d2c.order.model.Recharge;
import com.d2c.order.query.RechargeSearcher;

public interface RechargeService {

    /**
     * 根据id查询充值单
     *
     * @param id
     * @return
     */
    Recharge findById(Long id);

    /**
     * 根据sn查询充值单
     *
     * @param sn
     * @return
     */
    Recharge findBySn(String sn);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<RechargeDto> findBySearch(RechargeSearcher searcher, PageModel page);

    /**
     * 根据searcher数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(RechargeSearcher searcher);

    /**
     * 新增充值
     *
     * @param recharge
     * @param onlineRecharge
     * @return
     */
    Recharge insert(Recharge recharge, boolean onlineRecharge);

    /**
     * 充值审核通过
     *
     * @param id
     * @param submitor
     * @param paySn
     * @return
     */
    int doSubmit(Long id, String submitor, String paySn);

    /**
     * 充值取消审核
     *
     * @param rechargeId
     * @param submitor
     * @return
     */
    int doClose(Long rechargeId, String closer, String closeReason);

}

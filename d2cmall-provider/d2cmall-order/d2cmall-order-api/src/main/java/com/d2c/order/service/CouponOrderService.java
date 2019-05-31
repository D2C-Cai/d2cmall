package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.CouponOrderDto;
import com.d2c.order.model.CouponOrder;
import com.d2c.order.query.CouponOrderSearcher;

import java.math.BigDecimal;

public interface CouponOrderService {

    /**
     * 根据sn查询
     *
     * @param sn
     * @return
     */
    CouponOrder findBySn(String sn);

    /**
     * 根据id和会员id查询
     *
     * @param id
     * @param memberId
     * @return
     */
    CouponOrderDto findByIdAndMemberId(Long id, Long memberId);

    /**
     * 根据sn和会员id查询
     *
     * @param sn
     * @param memberId
     * @return
     */
    CouponOrderDto findBySnAndMemberId(String sn, Long memberId);

    /**
     * 根据会员id和优惠券id查询
     *
     * @param memberId
     * @param couponDefId
     * @return
     */
    CouponOrder findByMemberIdAndCouponDefId(Long memberId, Long couponDefId);

    /**
     * 通过查询条件查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CouponOrder> findBySearcher(CouponOrderSearcher searcher, PageModel page);

    /**
     * 通过查询条件，查出符合条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(CouponOrderSearcher searcher);

    /**
     * 创建订单
     *
     * @param couponOrder
     * @return
     */
    CouponOrder insert(CouponOrder couponOrder);

    /**
     * 支付成功
     *
     * @param paymentId
     * @param paySn
     * @param paymentType
     * @param orderSn
     * @param payedAmount
     * @return
     */
    int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount);

}

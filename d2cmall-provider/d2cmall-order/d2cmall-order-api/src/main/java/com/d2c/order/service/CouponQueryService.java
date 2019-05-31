package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.CouponDto;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.query.CouponSearcher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CouponQueryService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Coupon findById(Long id);

    /**
     * 通过编码和使用的会员，查找出对应的优惠券数据
     *
     * @param code     编码
     * @param memberId 会员id
     * @return
     */
    Coupon findByCode(String code, Long memberId);

    /**
     * 通过订单的id和优惠券状态，查找出对应的优惠券
     *
     * @param orderId 订单id
     * @param status  状态
     * @return
     */
    Coupon findByOrderId(Long orderId, CouponStatus status);

    /**
     * 通过会员的id，优惠券的状态，查找出符合会员的优惠券
     *
     * @param memberId 会员id
     * @param status   状态数据
     * @param page     分页
     * @return
     */
    PageResult<Coupon> findByMemberId(Long memberId, String[] status, PageModel page);

    /**
     * 通过会员的id，优惠券的状态，查找出符合会员的优惠券
     *
     * @param memberId 会员id
     * @param status   状态数据
     * @return
     */
    int countByMemberId(Long memberId, String[] status);

    /**
     * 通过会员和订单的条件判断，查找出哪些是可以用的优惠券，哪些是不可用的优惠券
     *
     * @param memberInfoId
     * @param totalAmount
     * @return
     */
    PageResult<Coupon> findMyUnusedCoupons(Long memberInfoId, BigDecimal totalAmount);

    /**
     * 找出已经过期固定天数的优惠券的统计数据
     *
     * @param days 天数
     * @return
     */
    List<Map<String, Object>> findExpireCoupon(int days);

    /**
     * 通过优惠券的查询条件和分页条件，得到封装优惠券数据的dto分页数据
     *
     * @param searcher 查询条件
     * @param page     分页条件
     * @return
     */
    PageResult<CouponDto> findBySearcher(CouponSearcher searcher, PageModel page);

    /**
     * 通过查询条件，查出符合条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(CouponSearcher searcher);

    /**
     * 通过查询条件，找出一定的简单的优惠券数据
     *
     * @param searcher
     * @param page
     * @return
     */
    List<HelpDTO> findHelpDto(CouponSearcher searcher, PageModel page);

    /**
     * 找出通过微信码和定义的id符合的数量
     *
     * @param wxCode   微信码
     * @param defineId 优惠券定义的id
     * @return
     */
    int countByWxCodeAndDefine(String wxCode, Long defineId);

    /**
     * 统计优惠券的数量
     *
     * @param memberInfoId
     * @return
     */
    Map<String, Object> countGroupByStatus(Long memberInfoId);

}

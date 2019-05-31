package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.query.CouponSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CouponMapper extends SuperMapper<Coupon> {

    Coupon findByCode(@Param("code") String code);

    Coupon findCouponByCode(@Param("code") String code, @Param("memberId") Long memberId);

    List<Coupon> findByDefineId(@Param("defineid") Long defineid, @Param("pager") PageModel page);

    Coupon findByOrderIdAndStatus(@Param("orderId") Long orderId, @Param("status") CouponStatus status);

    List<Coupon> findBySearcher(@Param("searcher") CouponSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") CouponSearcher searcher);

    List<Map<String, Object>> findExpireCoupon(@Param("days") int days);

    List<Coupon> findByMemberId(@Param("memberId") Long memberId, @Param("status") String[] status,
                                @Param("expireDateStart") Date expireDateStart, @Param("expireDateEnd") Date expireDateEnd,
                                @Param("pager") PageModel pager);

    int countByMemberId(@Param("memberId") Long memberId, @Param("status") String[] status,
                        @Param("expireDateStart") Date expireDateStart, @Param("expireDateEnd") Date expireDateEnd);

    int countByWxCodeAndDefine(@Param("wxCode") String wxCode, @Param("defineId") Long defineId);

    int countMyCoupons(@Param("memberInfoId") Long memberInfoId, @Param("status") CouponStatus[] status);

    List<Map<String, Object>> countGropByStatus(Long memberInfoId);

    int doSendCoupon(@Param("id") Long id, @Param("sendor") String sendor, @Param("sendMark") String sendMark);

    int doSendCouponByDefineId(@Param("defId") Long defId, @Param("sendor") String sendor,
                               @Param("sendMark") String sendMark);

    int doGetBackCoupon(@Param("loginCode") String loginCode, @Param("memberInfoId") Long memberInfoId);

    int doActivateCoupon(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId, @Param("now") Date now);

    int doLockCoupon(Coupon coupon);

    int doUsedCoupon(Coupon coupon);

    int doInvalidCoupon(@Param("id") Long id);

    int doUnlockCoupon(@Param("orderId") Long orderId, @Param("id") Long id);

    int doReleaseCoupon(@Param("orderId") Long orderId, @Param("id") Long id);

    int doTransferCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId);

    int doCloseExpiredCoupon();

    int doCopy2CouponHistory();

    int doDeleteExpiredCoupon();

    int doTransfer(@Param("id") Long id, @Param("toMemberId") Long toMemberId,
                   @Param("toLoginCode") String toLoginCode);

}
package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CouponOrder;
import com.d2c.order.query.CouponOrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CouponOrderMapper extends SuperMapper<CouponOrder> {

    CouponOrder findBySn(@Param("sn") String sn);

    CouponOrder findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    CouponOrder findBySnAndMemberId(@Param("sn") String sn, @Param("memberId") Long memberId);

    CouponOrder findByMemberIdAndCouponDefId(@Param("memberId") Long memberId, @Param("couponDefId") Long couponDefId);

    List<CouponOrder> findBySearch(@Param("searcher") CouponOrderSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") CouponOrderSearcher searcher);

    int updatePaySuccess(@Param("id") Long id, @Param("paymentId") Long paymentId,
                         @Param("paymentType") Integer paymentType, @Param("paySn") String paySn,
                         @Param("payAmount") BigDecimal payAmount);

}

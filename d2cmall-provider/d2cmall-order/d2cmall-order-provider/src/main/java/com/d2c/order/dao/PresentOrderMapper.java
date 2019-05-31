package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.query.PresentOrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PresentOrderMapper extends SuperMapper<PresentOrder> {

    PresentOrder findBySn(@Param("sn") String sn);

    int updatePaySuccess(@Param("id") Long id, @Param("paymentId") Long paymentId,
                         @Param("paymentType") Integer paymentType, @Param("paySn") String paySn,
                         @Param("payAmount") BigDecimal payAmount);

    int doFinished(@Param("id") Long id);

    int countBySearcher(@Param("searcher") PresentOrderSearcher searcher);

    List<PresentOrder> findBySearcher(@Param("searcher") PresentOrderSearcher searcher, @Param("page") PageModel page);

    BigDecimal findVirtualPresentAmount(@Param("memberId") Long memberId);

    BigDecimal findActualPresentAmount(@Param("memberId") Long memberId);

    BigDecimal findVirtualGiveAmount(@Param("memberId") Long memberId);

    BigDecimal findActualGiveAmount(@Param("memberId") Long memberId);

}

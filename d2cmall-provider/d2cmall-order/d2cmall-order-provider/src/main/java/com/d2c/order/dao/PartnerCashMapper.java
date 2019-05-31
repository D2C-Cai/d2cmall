package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.query.PartnerCashSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerCashMapper extends SuperMapper<PartnerCash> {

    PartnerCash findBySn(String sn);

    PartnerCash findActiveByMobile(@Param("loginCode") String loginCode);

    BigDecimal findApplyCashByPartnerId(Long partnerId);

    BigDecimal findWithCashByDate(@Param("partnerId") Long partnerId, @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate, @Param("taxType") Integer taxType);

    List<PartnerCash> findBySearcher(@Param("searcher") PartnerCashSearcher searcher, @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") PartnerCashSearcher searcher);

    List<Map<String, Object>> findCountGroupByStatus();

    PartnerCash findLastSuccessOne(@Param("partnerId") Long partnerId, @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate, @Param("taxType") Integer taxType);

    int doAgree(@Param("id") Long id, @Param("lastModifyMan") String confirmMan,
                @Param("confirmOperateMan") String confirmOperateMan);

    int doRefuse(@Param("id") Long id, @Param("refuseReason") String refuseReason,
                 @Param("lastModifyMan") String confirmMan, @Param("confirmOperateMan") String confirmOperateMan);

    int doPay(@Param("id") Long id, @Param("paySn") String paySn, @Param("payAmount") BigDecimal payAmount,
              @Param("payMan") String payMan, @Param("payDate") Date payDate);

}

package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.PartnerBill;
import com.d2c.order.query.PartnerBillSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerBillMapper extends SuperMapper<PartnerBill> {

    PartnerBill findByOrderItemId(@Param("orderItemId") Long orderItemId);

    List<PartnerBill> findBySearcher(@Param("searcher") PartnerBillSearcher searcher, @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") PartnerBillSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int doClose(@Param("orderItemId") Long orderItemId);

    Map<String, Object> findPartnerSummaryToday(@Param("partnerId") Long partnerId, @Param("beginDate") Date beginDate,
                                                @Param("endDate") Date endDate, @Param("status") Integer[] status);

    Map<String, Object> findChildrenSummaryToday(@Param("partnerId") Long partnerId, @Param("beginDate") Date beginDate,
                                                 @Param("endDate") Date endDate, @Param("status") Integer[] status);

    List<Map<String, Object>> findBillSummary(@Param("id") Long id, @Param("rid") String rid,
                                              @Param("ratio") String ratio);

    List<Map<String, Object>> findCountGroupByStatus(@Param("beginDate") Date beginDate,
                                                     @Param("endDate") Date endDate);

    List<Map<String, Object>> findCountGroupByLevel(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

}

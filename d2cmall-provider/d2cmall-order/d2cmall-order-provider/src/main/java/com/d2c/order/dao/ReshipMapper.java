package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Reship;
import com.d2c.order.query.ReshipSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReshipMapper extends SuperMapper<Reship> {

    Reship findBySn(@Param("reshipSn") String reshipSn);

    Reship findByIdAndMemberInfoId(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    List<Reship> findByDeliverySn(@Param("nu") String nu);

    List<Reship> findBySearch(@Param("searcher") ReshipSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ReshipSearcher searcher);

    Map<String, Object> findAmountByDesigner(@Param("designerId") Long designerId);

    List<Reship> findReshipForStatement(@Param("beginDate") Date date, @Param("page") PageModel page);

    int countReshipForStatement(@Param("beginDate") Date beginDate);

    List<Reship> findNotDeliveryClose();

    List<Reship> findNotDeliveryRemind(Integer day);

    List<Map<String, Object>> countByStatusAndMemberId(@Param("memberInfoId") Long memberInfoId);

    List<Map<String, Object>> countGroupByStatus();

    int doLogistic(@Param("id") Long id, @Param("deliverySn") String deliverySn,
                   @Param("deliveryCorpName") String deliveryCorpName, @Param("memo") String memo);

    int doCancel(@Param("id") Long id, @Param("closer") String closer, @Param("closeDate") Date closeDate);

    int doCustomAgree(@Param("id") Long id, @Param("auditor") String auditor, @Param("auditDate") Date auditDate,
                      @Param("customerMemo") String customerMemo, @Param("deliveryExpiredDate") Date deliveryExpiredDate);

    int doCustomerRefuse(@Param("id") Long id, @Param("closer") String closer, @Param("closeDate") Date closeDate,
                         @Param("customerMemo") String customerMemo);

    int doCustomerClose(@Param("id") Long id, @Param("closer") String closer, @Param("closeDate") Date closeDate,
                        @Param("customerMemo") String customerMemo);

    int doStoreAgree(@Param("id") Long id, @Param("refundId") Long refundId, @Param("receiver") String receiver,
                     @Param("receiveDate") Date receiveDate, @Param("customerMemo") String customerMemo);

    int doStoreRefuse(@Param("id") Long id, @Param("refuseReceiver") String refuseReceiver,
                      @Param("refuseReceiveDate") Date refuseReceiveDate, @Param("customerMemo") String customerMemo);

    int updateBackAddress(@Param("id") Long id, @Param("backAddress") String backAddress,
                          @Param("backMobile") String backMobile, @Param("backConsignee") String backConsignee);

}

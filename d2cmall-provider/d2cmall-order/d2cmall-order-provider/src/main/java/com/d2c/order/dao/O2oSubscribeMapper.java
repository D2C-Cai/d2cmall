package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.query.O2oSubscribeSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface O2oSubscribeMapper extends SuperMapper<O2oSubscribe> {

    O2oSubscribe findUnSubmit(Long memberId);

    O2oSubscribe findByIdAndStoreId(@Param("id") Long id, @Param("storeId") Long storeId);

    O2oSubscribe findLastO2oSubscribe(@Param("memberId") Long memberId);

    List<O2oSubscribe> findBySearch(@Param("searcher") O2oSubscribeSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") O2oSubscribeSearcher searcher);

    Map<String, Object> findAnalysisBySearch(@Param("searcher") O2oSubscribeSearcher searcher);

    int countSubscribeTimes(@Param("subscribe") O2oSubscribe subscribe);

    List<Map<Integer, Integer>> countGroupByStatus(@Param("storeId") Long storeId);

    int updateVisitById(@Param("id") Long id, @Param("visit") String visit);

    int updateRemarkById(@Param("id") Long id, @Param("remark") String remark);

    int updateCusCostById(@Param("id") Long id, @Param("status") Integer status, @Param("commentId") Long commentId,
                          @Param("cusCost") BigDecimal cusCost);

    int deleteByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    int cancel(@Param("id") Long id, @Param("cancelMan") String cancelMan, @Param("cancelReason") String cancelReason);

    int notice(@Param("id") Long id, @Param("noticeMan") String noticeMan);

    int receive(@Param("id") Long id, @Param("receiveMan") String receiveMan);

    int ready(@Param("id") Long id);

    int complete(@Param("id") Long id, @Param("completeMan") String completeMan, @Param("feedback") String feedback,
                 @Param("payAmount") BigDecimal payAmount, @Param("payStatus") Integer payStatus,
                 @Param("actualNumbers") Integer actualNumbers, @Param("retailSn") String retailSn);

    int doCancelByMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

}

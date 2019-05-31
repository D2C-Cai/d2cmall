package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Refund;
import com.d2c.order.query.RefundSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 退款的Mapper
 *
 * @author xh
 */
public interface RefundMapper extends SuperMapper<Refund> {

    Refund findByRefundSn(String refundSn);

    Refund findByIdAndMemberInfoId(@Param("refundId") Long refundId, @Param("memberInfoId") Long memberInfoId);

    List<Refund> findBySearch(@Param("searcher") RefundSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") RefundSearcher searcher);

    List<Map<String, Object>> countByStatusAndMemberId(@Param("memberInfoId") Long memberInfoId);

    List<Map<String, Object>> countGroupByStatus();

    int updateReshipId(@Param("refundId") Long refundId, @Param("reshipId") Long reshipId);

    int updateBackAccount(@Param("refundId") Long refundId, @Param("backAccountSn") String backAccountSn,
                          @Param("backAccountName") String backAccountName);

    int updateAmount(@Param("totalAmount") BigDecimal totalAmount, @Param("id") Long id);

    int updateWalletAmount(@Param("id") Long id, @Param("cashAmount") BigDecimal cashAmount,
                           @Param("giftAmount") BigDecimal giftAmount);

    int updateErrorCode(@Param("refundSn") String refundSn, @Param("errorCode") String errorCode);

    int doCustomerAgree(@Param("refundId") Long refundId, @Param("totalAmount") BigDecimal totalAmount,
                        @Param("auditor") String auditor, @Param("customerMemo") String customerMemo);

    int doCloseRefund(@Param("refundId") Long refundId, @Param("closer") String closer);

    int doLock(@Param("refundId") Long refundId, @Param("lastModifyMan") String lastModifyMan);

    int doCancelLock(@Param("refundId") Long refundId, @Param("lastModifyMan") String lastModifyMan);

    int doBack(@Param("refundId") Long refundId, @Param("lastModifyMan") String lastModifyMan);

    int doCancel(@Param("refundId") Long refundId, @Param("memberName") String memberName);

    int doSuccessRefund(@Param("refundId") Long refundId, @Param("backAccountType") Integer backAccountType,
                        @Param("payDate") Date payDate, @Param("payMoney") BigDecimal payMoney, @Param("paySn") String paySn,
                        @Param("payer") String payer, @Param("allRefund") int allRefund);

    int doThirdRefund(@Param("refundId") Long refundId, @Param("backAccountType") Integer backAccountType,
                      @Param("payDate") Date payDate, @Param("payMoney") BigDecimal payMoney, @Param("payer") String payer);

    int doThirdSuccess(@Param("refundId") Long refundId, @Param("paySn") String paySn,
                       @Param("notifyTime") Date notifyTime);

    int doCompensation(@Param("id") Long id, @Param("compansation") BigDecimal compansation);

}

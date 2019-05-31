package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Statement;
import com.d2c.order.query.StatementSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatementMapper extends SuperMapper<Statement> {

    Statement findBySn(@Param("sn") String sn);

    List<Statement> findByDesigner(@Param("settleYear") int settleYear, @Param("settleMonth") int settleMonth,
                                   @Param("periodOfMonth") int periodOfMonth, @Param("fromType") String fromType,
                                   @Param("designerId") Long designerId);

    List<Map<String, Object>> countByDesigner(@Param("settleYear") int settleYear,
                                              @Param("settleMonth") int settleMonth, @Param("periodOfMonth") int periodOfMonth,
                                              @Param("fromType") String fromType, @Param("designerId") Long designerId);

    List<Statement> findBySearcher(@Param("searcher") StatementSearcher searcher, @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") StatementSearcher searcher);

    int updateStatus(@Param("id") Long statementId, @Param("status") Integer status,
                     @Param("oldStatus") Integer oldStatus);

    int updateDesignerMemo(@Param("id") Long id, @Param("designerMemo") String designerMemo);

    int updateAdminMemo(@Param("id") Long id, @Param("adminMemo") String adminMemo);

    int updateSettle(@Param("settleAmount") BigDecimal settlePrice, @Param("tagPrice") BigDecimal tagPrice,
                     @Param("quantity") Integer quantity, @Param("operator") String operator, @Param("id") Long id);

    int doBack(@Param("id") Long id, @Param("memo") String memo);

    int doSuccess(@Param("id") Long id, @Param("operator") String operator, @Param("finishDate") Date finishDate);

    BigDecimal sumByDesigner(@Param("statementId") Long statementId);

    int doSend(@Param("id") Long id, @Param("operator") String operator);

    int doPay(@Param("payer") String payer, @Param("paySn") String paySn, @Param("payDate") Date payDate,
              @Param("id") Long id, @Param("payBank") String payBank, @Param("payPic") String payPic,
              @Param("operator") String operator, @Param("payMoney") BigDecimal payMoney,
              @Param("payMemo") String payMemo);

    int doApply(@Param("id") Long id, @Param("flag") Integer flag, @Param("operator") String operator);

    int doRetreat(@Param("id") Long id, @Param("operator") String operator);

    int updateCompensation(@Param("id") Long id, @Param("compensationCount") Integer compensationCount,
                           @Param("compensationAmount") BigDecimal compensationAmount,
                           @Param("actualCompensationAmount") BigDecimal actualCompensationAmount);

}

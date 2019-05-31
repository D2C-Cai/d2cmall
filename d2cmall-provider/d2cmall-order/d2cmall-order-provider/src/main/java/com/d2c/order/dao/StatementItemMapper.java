package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.StatementItem;
import com.d2c.order.query.StatementItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StatementItemMapper extends SuperMapper<StatementItem> {

    int countBySearcher(@Param("searcher") StatementItemSearcher searcher);

    List<StatementItem> findBySearcher(@Param("searcher") StatementItemSearcher searcher,
                                       @Param("page") PageModel page);

    int deleteById(@Param("id") Long id);

    int updateByStatementId(@Param("statementId") Long statementId, @Param("status") Integer status,
                            @Param("oldStatus") Integer oldStatus, @Param("operator") String operator);

    int doSend(@Param("id") Long statementId, @Param("settleYear") int settleYear,
               @Param("settleMonth") int settleMonth, @Param("periodOfMonth") int periodOfMonth,
               @Param("fromType") String fromType, @Param("designerId") Long designerId,
               @Param("operator") String operator);

    int updateSettleDate(@Param("settleYear") Integer settleYear, @Param("settleMonth") Integer settleMonth,
                         @Param("settleDay") Integer settleDay, @Param("id") Long id, @Param("operator") String operator);

    StatementItem findBySnAndBarCode(@Param("orderSn") String orderSn, @Param("barCode") String barCode);

    int updateAdminMemo(@Param("id") Long id, @Param("adminMemo") String memo);

    int updateDesignerMemo(@Param("id") Long id, @Param("designerMemo") String designerMemo);

    int updateSettlePrice(@Param("id") Long id, @Param("settlePrice") BigDecimal settlePrice,
                          @Param("operator") String operator);

    int reSend(@Param("statementId") Long statementId, @Param("operator") String operator);

    StatementItem findLastOne(@Param("type") Integer type);

    List<StatementItem> findEmptySettle(@Param("settleYear") int settleYear, @Param("settleMonth") int settleMonth,
                                        @Param("periodOfMonth") int periodOfMonth, @Param("fromType") String fromType,
                                        @Param("designerId") Long designerId);

    int doRetreat(@Param("statementId") Long statementId, @Param("operator") String operator);

}

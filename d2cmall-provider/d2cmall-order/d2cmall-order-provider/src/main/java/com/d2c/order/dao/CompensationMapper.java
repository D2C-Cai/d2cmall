package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.dto.CompensationSummaryDto;
import com.d2c.order.model.Compensation;
import com.d2c.order.query.CompensationSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CompensationMapper extends SuperMapper<Compensation> {

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<Compensation> findBySearcher(@Param("searcher") CompensationSearcher searcher, @Param("page") PageModel page);

    /**
     * 分页统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") CompensationSearcher searcher);

    /**
     * 赔偿汇总列表
     *
     * @param searcher
     * @param page
     * @return
     */
    List<CompensationSummaryDto> findSummary(@Param("searcher") CompensationSearcher searcher,
                                             @Param("page") PageModel page);

    /**
     * 赔偿汇总总条数
     *
     * @param searcher
     * @return
     */
    int countSummary(@Param("searcher") CompensationSearcher searcher);

    /**
     * 根据statementId查询
     *
     * @param statementId
     * @return
     */
    List<Long> findByStatement(@Param("statementId") Long statementId, @Param("page") PageModel page);

    /**
     * 根据statementId查询
     *
     * @param statementId
     * @return
     */
    int countByStatement(@Param("statementId") Long statementId);

    /**
     * 查找已支付信息
     *
     * @param designerId
     * @return
     */
    Map<String, Object> findPaySummary(@Param("designerId") Long designerId,
                                       @Param("searcher") CompensationSearcher searcher);

    /**
     * 结算
     *
     * @param id
     * @param operator
     * @return
     */
    int doBalance(@Param("id") Long id, @Param("operator") String operator);

    /**
     * 更新赔偿价
     *
     * @param id
     * @param price
     * @param operator
     * @return
     */
    int updatePrice(@Param("id") Long id, @Param("actualAmount") BigDecimal actualAmount,
                    @Param("operator") String operator);

    /**
     * 更新备注
     *
     * @param id
     * @param remark
     * @return
     */
    int updateRemark(@Param("id") Long id, @Param("remark") String remark);

    /**
     * 查找是否已有同一调拨单的同一门店的赔偿
     *
     * @param requisitionItemId
     * @param storeId
     * @return
     */
    Compensation findStoreRequisition(@Param("requisitionItemId") Long requisitionItemId,
                                      @Param("storeId") Long storeId);

    /**
     * 查找是否已存在同一调拨同一门店的赔偿
     *
     * @param requisitionItemId
     * @param designerId
     * @return
     */
    Compensation findDesignerCompensation(@Param("requisitionItemId") Long requisitionItemId,
                                          @Param("designerId") Long designerId);

    /**
     * 设计师赔偿单关联对账单
     *
     * @param statementId
     * @param year
     * @param month
     * @return
     */
    int updateStatement(@Param("statementId") Long statementId, @Param("year") int year, @Param("month") int month,
                        @Param("periodOfMonth") int periodOfMonth, @Param("designerId") Long designerId);

    /**
     * 根据条件计算赔偿数据
     *
     * @param searcher
     * @return
     */
    Map<String, Object> calculateBySearcher(@Param("searcher") CompensationSearcher searcher);

    /**
     * 结算对账单时结算赔偿单
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doBalanceByStatement(@Param("statementId") Long statementId, @Param("operator") String operator);

    /**
     * 根据订单明细查询设计师赔偿单
     *
     * @param orderItem
     * @return
     */
    Compensation findDesignerByOrderItemId(@Param("orderItemId") Long orderItemId);

}

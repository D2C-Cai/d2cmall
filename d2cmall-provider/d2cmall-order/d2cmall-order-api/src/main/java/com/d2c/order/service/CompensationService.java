package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.CompensationDto;
import com.d2c.order.dto.CompensationSummaryDto;
import com.d2c.order.model.Compensation;
import com.d2c.order.model.Compensation.CompensateMethod;
import com.d2c.order.query.CompensationSearcher;

import java.math.BigDecimal;
import java.util.Map;

public interface CompensationService {

    /**
     * 插入
     *
     * @param compensation
     * @return
     */
    Compensation insert(Compensation compensation, CompensateMethod method);

    /**
     * 查询汇总
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CompensationSummaryDto> findSummary(CompensationSearcher searcher, PageModel page);

    /**
     * 列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CompensationDto> findBySearcher(CompensationSearcher searcher, PageModel page);

    /**
     * 结算
     *
     * @param id
     * @param username
     * @return
     */
    int doBalance(Long id, String username);

    /**
     * 更新结算价格
     *
     * @param id
     * @param price
     * @param username
     * @return
     */
    int updatePrice(Long id, BigDecimal price, String username);

    /**
     * 更新备注
     *
     * @param id
     * @param remark
     * @param username
     * @return
     */
    int updateRemark(Long id, String remark, String username);

    /**
     * 明细统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(CompensationSearcher searcher);

    /**
     * 汇总统计
     *
     * @param searcher
     * @return
     */
    int countSummary(CompensationSearcher searcher);

    /**
     * 关联对账单
     *
     * @param statementId
     * @param year
     * @param month
     * @return
     */
    int updateStatement(Long statementId, int year, int month, int periodOfMonth, Long designerId);

    /**
     * 根据条件计算赔偿数据
     *
     * @param searcher
     * @return
     */
    Map<String, Object> calculateBySearcher(CompensationSearcher searcher);

    /**
     * 结算对账单时结算赔偿单
     *
     * @param statementId
     * @param balanceMan
     * @return
     */
    int doBalanceByDesigner(Long statementId, String balanceMan);

    /**
     * 根据statementId查询
     *
     * @param statementId
     * @param page
     * @return
     */
    PageResult<Long> findByStatement(Long statementId, PageModel page);

    /**
     * 根据订单明细id查询设计师赔偿单
     *
     * @param orderItemId
     * @return
     */
    Compensation findDesignerByOrderItemId(Long orderItemId);

}

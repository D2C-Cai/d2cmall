package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.StatementDto;
import com.d2c.order.model.Statement;
import com.d2c.order.query.StatementSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatementService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Statement findById(Long id);

    /**
     * 根据id查找DTO
     *
     * @param id
     * @return
     */
    StatementDto findDtoById(Long id);

    /**
     * 根据sn查找
     *
     * @param sn
     * @return
     */
    Statement findBySn(String sn);

    /**
     * 根据时间查找
     *
     * @param settleYear
     * @param settleMonth
     * @param designerId
     * @return
     */
    List<Statement> findByDesigner(int settleYear, int settleMonth, int periodOfMonth, String fromType,
                                   Long designerId);

    /**
     * 根据时间查询数量
     *
     * @param settleYear
     * @param settleMonth
     * @param designerId
     * @return
     */
    List<Map<String, Object>> countByDesigner(int settleYear, int settleMonth, int periodOfMonth, String type,
                                              Long designerId);

    /**
     * 根据searcher查找
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<StatementDto> findBySearcher(StatementSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(StatementSearcher searcher);

    /**
     * 生成对账单
     *
     * @param settleYear  结算年
     * @param settleMonth 结算月
     * @param designer    设计师
     * @return
     */
    int createStatement(Statement statement);

    /**
     * 新增对账单
     *
     * @param statement
     * @return
     */
    Statement insert(Statement statement);

    /**
     * 更新设计师备注
     *
     * @param statementId
     * @param memo
     * @param operator
     * @return
     */
    int updateDesignerMemo(Long statementId, String memo, String operator);

    /**
     * 更新客服备注
     *
     * @param statementId
     * @param memo
     * @param operator
     * @return
     */
    int updateAdminMemo(Long statementId, String memo, String operator);

    /**
     * 更新结算信息
     *
     * @param settlePrice
     * @param tagPrice
     * @param quantity
     * @param operator
     * @param id
     * @return
     */
    int updateSettle(BigDecimal settlePrice, BigDecimal tagPrice, Integer quantity, String operator, Long id);

    /**
     * 设计师退回
     *
     * @param statementId
     * @param memo
     * @param operator
     * @return
     */
    int doBack(Long statementId, String memo, String operator);

    /**
     * 设计师确认对账单
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doConfirm(Long statementId, String operator);

    /**
     * 支付对账单
     *
     * @param payer
     * @param paySn
     * @param payDate
     * @param statementId
     * @param payBank
     * @param payPic
     * @param operator
     * @param payMoney
     * @param payMemo
     * @return
     */
    int doPay(String payer, String paySn, Date payDate, Long statementId, String payBank, String payPic,
              String operator, BigDecimal payMoney, String payMemo);

    /**
     * 设计师接收对账单
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doReceive(Long statementId, String operator);

    /**
     * 发送对账单
     *
     * @param id
     * @param username
     * @param adminMemo
     * @return
     */
    int doSend(Long id, String username, String adminMemo);

    /**
     * 结算对账单
     *
     * @param id
     * @param operator
     * @param payMemo
     * @return
     */
    int doSuccess(Long id, String operator);

    /**
     * 是否申请用款
     *
     * @param id
     * @param status
     * @param username
     * @return
     */
    int doApply(Long id, Integer flag, String operator, String memo);

    /**
     * 财务在设计师待接收之前撤回对账单
     *
     * @param id
     * @param memo
     * @param username
     * @return
     */
    int doRetreat(Long id, String memo, String username);

    /**
     * 查找对账单
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Statement> findSimpleBySearcher(StatementSearcher searcher, PageModel page);

    /**
     * 重新计算对账单赔偿金额
     *
     * @param id
     * @return
     */
    int updateCompensation(Long id);

}

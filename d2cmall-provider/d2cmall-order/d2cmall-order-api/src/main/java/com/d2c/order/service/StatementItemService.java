package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.StatementItem;
import com.d2c.order.query.StatementItemSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface StatementItemService {

    /**
     * 插入对账商品明细单
     *
     * @param statemenyItem
     * @return
     */
    StatementItem insert(StatementItem statementItem);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<StatementItem> findBySearcher(StatementItemSearcher searcher, PageModel page);

    /**
     * 删除对账单商品明细表
     *
     * @param id
     * @return
     */
    int deleteById(Long id, String operaror);

    /**
     * 根据对账单id更新明细状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateByStatementId(Long statementId, Integer status, Integer oldStatus, String operator);

    /**
     * 驳回对账单
     *
     * @param statementId
     * @return
     */
    int doBack(Long statementId, String operator);

    /**
     * 确认对账单
     *
     * @param statementId
     * @return
     */
    int doConfirm(Long statementId, String operator);

    /**
     * 根据id查询对象
     *
     * @param id
     * @return
     */
    StatementItem findById(Long id);

    /**
     * 查询是否存在相同的对账单明细
     *
     * @param orderSn
     * @param barCode
     * @return
     */
    StatementItem findBySnAndBarCode(String orderSn, String barCode);

    /**
     * 修改结算日期
     *
     * @param settleYear
     * @param settleMonth
     * @param id
     * @param operator
     * @return
     */
    int updateSettleDate(Integer settleYear, Integer settleMonth, Integer settleDay, Long id, String operator);

    /**
     * 支付完成
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doSuccess(Long statementId, String operator);

    /**
     * 客服更新备注
     *
     * @param statemnetItemId
     * @param adminMemo
     * @return
     */
    int updateAdminMemo(Long statementItemId, String adminMemo, String operator);

    /**
     * 设计师更新备注
     *
     * @param statementItemId
     * @param adminMemo
     * @return
     */
    int updateDesignerMemo(Long statementItemId, String adminMemo, String operator);

    /**
     * 接收
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doReceive(Long statementId, String operator);

    /**
     * 导入结算单价
     *
     * @param id
     * @param settlePrice
     * @return
     */
    int updateSettlePrice(Long id, BigDecimal settlePrice, String operator);

    /**
     * 驳回的账单发送
     *
     * @param statementId
     * @return
     */
    int reSend(Long statementId, String operator);

    /**
     * 是否存在记录
     */
    StatementItem findLastOne(Integer type);

    int countBySearcher(StatementItemSearcher searcher);

    List<StatementItem> findEmptySettle(int settleYear, int settleMonth, int periodOfMonth, String fromType,
                                        Long designerId);

    /**
     * 撤回对账单
     *
     * @param statementId
     * @param operator
     * @return
     */
    int doRetreat(Long statementId, String operator);

    int doSend(Long id, int year, int month, int periodOfMonth, String fromType, Long designerId, String creator);

}

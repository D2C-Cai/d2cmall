package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Recharge;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.AccountItemSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountItemService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AccountItem findById(Long id);

    /**
     * 根据sn查询
     *
     * @param sn
     * @return
     */
    AccountItem findBySn(String sn);

    /**
     * 查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    List<AccountItem> findList(AccountItemSearcher searcher, PageModel page);

    /**
     * 根据search查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AccountItem> findBySearch(AccountItemSearcher searcher, PageModel page);

    /**
     * 根据search查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(AccountItemSearcher searcher);

    /**
     * 根据业务sn查询
     *
     * @param transactionSn
     * @param payType
     * @return
     */
    AccountItem findByTransactionSn(String transactionSn, String payType);

    /**
     * 更新业务信息
     *
     * @param itemId
     * @param transactionId
     * @param transactionInfo
     * @return
     */
    int updateTransactionInfo(Long itemId, Long transactionId, String transactionInfo);

    /**
     * 查询钱包消费情况
     *
     * @param calculateDate
     * @return
     */
    List<Map<String, Object>> findWalletAmount(Date calculateDate);

    /**
     * 创建账单
     *
     * @param item
     * @return
     */
    AccountItem insert(AccountItem item);

    /**
     * 账单确认
     *
     * @param payId
     * @param man
     * @return
     */
    int doConfirm(Long payId, String man);

    /**
     * 账单取消
     *
     * @param itemId
     * @param username
     * @param closeInfo
     * @return
     */
    int doCancel(Long itemId, String username, String closeInfo);

    /**
     * 充值
     *
     * @param recharge
     * @return
     */
    AccountItem doRecharge(Recharge recharge);

    /**
     * 提现
     *
     * @param drawCash
     * @return
     */
    AccountItem doWithdrawCash(WithdrawCash drawCash);

    BigDecimal sumTotalAmount(Long sourceId);

    BigDecimal sumTotalGiftAmount(Long sourceId);

}

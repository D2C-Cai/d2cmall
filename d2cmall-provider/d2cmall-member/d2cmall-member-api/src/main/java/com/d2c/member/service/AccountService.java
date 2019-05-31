package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Account;
import com.d2c.member.query.AccountSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface AccountService {

    /**
     * 新增账号同时绑定手机
     *
     * @param account
     * @return
     */
    Account insert(Account account);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Account findById(Long accountId);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Account> findBySearch(AccountSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(AccountSearcher searcher);

    /**
     * 根据memberId查询
     *
     * @param memberId
     * @return
     */
    Account findByMemberId(Long memberId);

    /**
     * 根据account查询
     *
     * @param account
     * @return
     */
    Account findByAccount(String account);

    /**
     * 计算总可兑额，总赠金
     *
     * @return
     */
    Map<String, BigDecimal> countAccountAmount();

    /**
     * 检查支付密码
     *
     * @param memberId
     * @param password
     * @return
     */
    String checkPassword(Long memberId, String password);

    /**
     * 调整账户收入金额
     *
     * @param accountId
     * @param cashAmount
     * @param giftAmount
     * @param limitGiftAmount
     * @param limitDate
     * @return
     */
    int doUpdateAmount(Long accountId, BigDecimal cashAmount, BigDecimal giftAmount, BigDecimal limitGiftAmount,
                       Date limitDate);

    /**
     * 电话绑定设置支付密码
     *
     * @param accountId
     * @param mobile
     * @param nationCode
     * @param newPassword2
     * @return
     */
    int doSetPassword(Long accountId, String mobile, String nationCode, String newPassword2);

    /**
     * 修改支付密码（前台）
     *
     * @param accountId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    int doChangePassword(Long accountId, String oldPassword, String newPassword);

    /**
     * 锁定账号
     *
     * @param accountId
     * @param locked
     * @return
     */
    int doLockAccount(Long accountId, Boolean locked);

    /**
     * 调整红包金额
     *
     * @param accountId
     * @param redAmount
     * @param redDate
     * @return
     */
    int doUpdateRedAmount(Long accountId, BigDecimal redAmount, Date redDate);

    /**
     * 活动到期增金清零
     *
     * @param account
     * @return
     */
    int doCleanLimitAmount(Long accountId);

    /**
     * 修改绑定手机
     *
     * @param memberId
     * @param newMobile
     * @return
     */
    int doChangeMobile(Long memberId, String newMobile, String nationCode);

    /**
     * 注销账户
     *
     * @param loginCode
     * @return
     */
    int doDelete(String loginCode);

    /**
     * 冻结账户
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, int status);

}

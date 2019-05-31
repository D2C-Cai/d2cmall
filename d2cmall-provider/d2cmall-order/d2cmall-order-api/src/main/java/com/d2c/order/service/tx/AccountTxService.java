package com.d2c.order.service.tx;

import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.support.OrderInfo;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.RedPacketsItem;

import java.math.BigDecimal;

public interface AccountTxService {

    /**
     * 消费
     *
     * @param orderInfo
     * @return
     */
    AccountItem doConsume(OrderInfo orderInfo);

    /**
     * 退款
     *
     * @param orderInfo
     * @return
     */
    AccountItem doRefund(OrderInfo orderInfo);

    /**
     * 推荐返利
     *
     * @param memberInfo
     * @param recId
     * @param rebates
     * @return
     */
    int doRecommend(MemberInfo memberInfo, Long recId, String rebates);

    /**
     * 活动清零
     *
     * @param account
     * @return
     */
    int doCleanLimit(Account account);

    /**
     * 冻结红包
     *
     * @param memberId
     * @param amount
     * @return
     */
    RedPacketsItem doFreezeRed(Long memberId, BigDecimal amount);

    /**
     * 退还红包
     *
     * @param id
     * @return
     */
    int doBackRed(Long id);

    /**
     * 赠送红包
     *
     * @param memberId
     * @param businessType
     * @param amount
     * @return
     */
    RedPacketsItem doSuccessRed(Long memberId, String businessType, BigDecimal amount);

}

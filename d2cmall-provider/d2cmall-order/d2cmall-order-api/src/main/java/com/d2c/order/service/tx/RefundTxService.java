package com.d2c.order.service.tx;

import java.math.BigDecimal;
import java.util.Date;

public interface RefundTxService {

    /**
     * 退款退货成功（钱包支付）
     *
     * @param refundId
     * @param payer
     * @param ip
     * @param allRefund
     * @param userId
     * @return
     */
    int doSuccessRefundWallet(Long refundId, String payer, String ip, int allRefund, Long userId);

    /**
     * 第三方支付平台支付回调
     *
     * @param refundSn
     * @param notifyTime
     * @param payMoney
     * @param paySn
     * @return
     */
    int doThirdSuccess(String refundSn, Date notifyTime, BigDecimal payMoney, String paySn);

    /**
     * 退款退货成功（除钱包外）
     *
     * @param refundId
     * @param backAccountType
     * @param payDate
     * @param payMoney
     * @param paySn
     * @param payer
     * @param ip
     * @param allRefund
     * @param userId
     * @return
     */
    int doSuccessRefund(Long refundId, Integer backAccountType, Date payDate, BigDecimal payMoney, String paySn,
                        String payer, String ip, int allRefund, Long userId);

}

package com.d2c.order.service.tx;

import java.math.BigDecimal;

public interface RechargeTxService {

    /**
     * 充值审核通过
     *
     * @param rechargeId
     * @param submitor
     * @return
     */
    int doSuccessRecharge(Long rechargeId, String submitor, String paySn);

    /**
     * 支付成功
     *
     * @param paymentId
     * @param paySn
     * @param paymentType
     * @param orderSn
     * @param payedAmount
     * @return
     */
    int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount);

}

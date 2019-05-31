package com.d2c.order.service.tx;

import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.base.IPaymentInterface;

import java.math.BigDecimal;

public interface PaymentTxService {

    /**
     * 钱包支付
     *
     * @param order
     * @param password
     * @param deviceType
     * @return
     */
    int doWalletPayment(IPaymentInterface order, String password, DeviceTypeEnum deviceType);

    /**
     * 支付成功
     *
     * @param orderType
     * @param orderSn
     * @param payAmount
     * @param paySn
     * @param payMan
     * @param payType
     * @param cashAmount
     * @param giftAmount
     * @param mchId
     * @param appId
     * @return
     */
    int doPaySuccess(String orderType, String orderSn, BigDecimal payAmount, String paySn, String payMan,
                     PaymentTypeEnum payType, BigDecimal cashAmount, BigDecimal giftAmount, String mchId, String appId);

}

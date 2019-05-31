package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.support.OrderInfo;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Payment;
import com.d2c.order.model.Payment.PaymentStatus;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.order.service.*;
import com.d2c.util.string.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service(protocol = {"dubbo"})
public class PaymentTxServiceImpl implements PaymentTxService {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private AuctionMarginService auctionMarginService;
    @Reference
    private AccountTxService accountTxService;
    @Reference
    private RechargeTxService rechargeTxService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doWalletPayment(IPaymentInterface order, String password, DeviceTypeEnum deviceType) {
        OrderInfo orderInfo = new OrderInfo(order.getBillSourceType(), PayTypeEnum.PAY.name());
        BeanUtils.copyProperties(order, orderInfo, "memo");
        AccountItem accountItem = accountTxService.doConsume(orderInfo);
        return this.doPaySuccess(order.getBillSourceType(), order.getBillSourceSn(), order.getBillTotalFee(),
                accountItem.getSn(), String.valueOf(order.getMemberId()), PaymentTypeEnum.WALLET,
                accountItem.getAmount(), accountItem.getGiftAmount(), null, null);
    }

    /**
     * 支付成功+支付完成的回调接口
     *
     * @throws Exception
     */
    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doPaySuccess(String orderType, String orderSn, BigDecimal payAmount, String paySn, String payMan,
                            PaymentTypeEnum payType, BigDecimal cashAmount, BigDecimal giftAmount, String mchId, String appId) {
        int result = 0;
        Payment payment = paymentService.findByAlipaySn(paySn);
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        if (payment == null) {
            payment = new Payment();
            payment.setPaymentSn(RandomUtil.getUUID());
            payment.setPaymentStatus(PaymentStatus.success.name());
            payment.setPaymentType(payType.getCode());
            payment.setTotalAmount(payAmount);
            payment.setPaymentFee(new BigDecimal(0));
            payment.setPayer(payMan);
            payment.setPayDate(new Date());
            payment.setAlipaySn(paySn);
            payment.setOrderSn(orderSn);
            payment.setOrderType(orderType);
            payment.setMchId(mchId);
            payment.setAppId(appId);
            payment = paymentService.insert(payment);
        }
        if (payment != null && payment.getId() > 0) {
            if (orderType.equalsIgnoreCase(OrderTypeEnum.ORDER.name())) {
                // 商品订单
                result = orderService.doPaySuccess(orderSn, payment.getId(), paySn, payType.getCode(), payAmount,
                        cashAmount, giftAmount);
            } else if (orderType.equalsIgnoreCase(OrderTypeEnum.COLLAGE.name())) {
                // 拼团订单
                result = collageOrderService.doPaySuccess(orderSn, payment.getId(), paySn, payType.getCode(), payAmount,
                        cashAmount, giftAmount);
            } else if (orderType.equalsIgnoreCase(PayTypeEnum.RECHARGE.name())) {
                // 在线充值
                result = rechargeTxService.doPaySuccess(payment.getId(), paySn, payType.getCode(), orderSn, payAmount);
            } else if (orderType.equalsIgnoreCase(OrderTypeEnum.COUPON.name())) {
                // 购优惠券
                result = couponOrderService.doPaySuccess(payment.getId(), paySn, payType.getCode(), orderSn, payAmount);
            } else if (orderType.equalsIgnoreCase(OrderTypeEnum.MARGIN.name())) {
                // 拍卖相关
                result = auctionMarginService.doPaySuccess(payment.getId(), paySn, payType.getCode(), orderSn,
                        payAmount);
            } else {
                // 商品订单
                result = orderService.doPaySuccess(orderSn, payment.getId(), paySn, payType.getCode(), payAmount,
                        cashAmount, giftAmount);
            }
        }
        return result;
    }

}

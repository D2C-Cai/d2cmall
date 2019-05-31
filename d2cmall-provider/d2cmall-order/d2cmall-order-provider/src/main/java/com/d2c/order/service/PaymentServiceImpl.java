package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.core.annotation.AsyncMethod;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.PaymentMapper;
import com.d2c.order.dto.AbnormalPaymentDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Payment;
import com.d2c.order.query.AbnormalPaymentSearcher;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayConfig;
import com.d2c.order.third.payment.alipay.refund.AlipayRefundClient;
import com.d2c.order.third.payment.wxpay.core.WeixinRefundHelper;
import com.d2c.order.third.payment.wxpay.core.WxAppConfig;
import com.d2c.order.third.payment.wxpay.core.WxWapConfig;
import com.d2c.order.third.payment.wxpay.refund.RefundConfig301;
import com.d2c.order.third.payment.wxpay.refund.RefundConfig801;
import com.d2c.order.third.payment.wxpay.refund.WXPay;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xh
 * @see 支付记录
 */
@Service("paymentService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PaymentServiceImpl extends ListServiceImpl<Payment> implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private RefundConfig301 refundConfig301;
    @Autowired
    private RefundConfig801 refundConfig801;

    @Override
    public Payment findById(Long id) {
        return paymentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Payment findByAlipaySn(String alipaySn) {
        return paymentMapper.findByAlipaySn(alipaySn);
    }

    @Override
    public PageResult<Payment> findBySearcher(AbnormalPaymentSearcher searcher, PageModel page) {
        PageResult<Payment> pager = new PageResult<>(page);
        int totalCount = paymentMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Payment> list = paymentMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<AbnormalPaymentDto> findAbnormalPayment(AbnormalPaymentSearcher searcher, PageModel page) {
        PageResult<AbnormalPaymentDto> pager = new PageResult<>(page);
        int totalCount = paymentMapper.countAbnormalPayment(searcher);
        if (totalCount > 0) {
            List<AbnormalPaymentDto> list = paymentMapper.findAbnormalPayment(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Payment insert(Payment payment) {
        return this.save(payment);
    }

    @Override
    public int updateNotNull(Payment entity) {
        return super.updateNotNull(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSuccess(Long id, Long memberId) {
        return paymentMapper.doSuccess(id, memberId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doInvalid(Long paymentId) {
        return paymentMapper.doInvalid(paymentId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCancel(Long id) {
        return paymentMapper.doCancel(id);
    }

    @Override
    @AsyncMethod(delay = 3000)
    public String doThirdRefund(Long id, String sn, Long paymentId, String paySn, Integer paymentType,
                                BigDecimal payAmount) {
        String refundResult = "";
        if (paymentType == PaymentTypeEnum.ALIPAY.getCode()) {
            // 支付宝
            try {
                Date refundDate = new Date();
                SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
                refundResult = AlipayRefundClient.doRefund(alipayConfig, DateUtil.second2str(refundDate),
                        fm.format(refundDate) + id, "1", paySn + "^" + payAmount + "^" + "拼团失败退款");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else if (paymentType == PaymentTypeEnum.WXAPPPAY.getCode()
                || paymentType == PaymentTypeEnum.WX_SCANPAY.getCode()
                || paymentType == PaymentTypeEnum.WXPAY.getCode()) {
            // 微信支付
            try {
                Payment payment = paymentMapper.selectByPrimaryKey(paymentId);
                if (payment.getMchId() != null) {
                    WXPay client = null;
                    if (payment.getMchId().endsWith(WxAppConfig.MCH_TAIL)) {
                        refundConfig301.setAppID(payment.getAppId());
                        client = new WXPay(refundConfig301, refundConfig301.getRefund_notify_url(), false, false);
                    } else if (payment.getMchId().endsWith(WxWapConfig.MCH_TAIL)) {
                        refundConfig801.setAppID(payment.getAppId());
                        client = new WXPay(refundConfig801, refundConfig801.getRefund_notify_url(), false, false);
                    }
                    if (client != null) {
                        WeixinRefundHelper helper = new WeixinRefundHelper();
                        Map<String, String> response = client
                                .refund(helper.createParamMap(sn, sn, payAmount, payAmount));
                        refundResult = response.get("return_code");
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return refundResult;
    }

}

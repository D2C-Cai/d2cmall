package com.d2c.order.third.payment.alipay.core.pcwap;

import org.apache.commons.lang.StringUtils;

public class AlipayUtil {

    /**
     * 翻译错误码
     *
     * @param error
     * @return
     */
    public static String convertError(String error) {
        if (StringUtils.isNotEmpty(error)) {
            switch (error) {
                case "REFUND_CHARGE_FEE_ERROR":
                    return "退收金额不合法";
                case "BUYER_ERROR":
                    return "收款账号存在问题";
                case "TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH":
                    return "账号余额不足";
                case "TXN_RESULT_ACCOUNT_STATUS_NOT_VALID":
                    return "账号余额不足";
                case "SINGLE_DETAIL_DATA_EXCEED_LIMIT":
                    return "单笔退款明细超出限制";
                case "DUBL_TRADE_NO_IN_SAME_BATCH":
                    return "同一批退款中存在相同的退款记录";
                case "DUPLICATE_BATCH_NO":
                    return "不要重复提交退款申请";
                case "TRADE_STATUS_ERROR":
                    return "交易状态不允许退款";
                case "ACCOUNT_NOT_EXISTS":
                    return "账号不存在";
                case "TRADE_NOT EXISTS":
                    return "交易不存在";
                case "TRADE_HAS_CLOSE":
                    return "交易已关闭,不允许退款";
                case "TRADE_HAS_FINISHED":
                    return "交易已结束,不允许退款";
                case "REFUND_DATA_ERROR":
                    return "退款时间错误";
                case "BATCH_REFUND_LOCK_EEROR":
                    return "同一时间不允许进行多笔并发退款";
                case "REFUNF_SUBTRADE_FEE_ERRER":
                    return "退子交易金额不合法";
                case "RESULT_FACE_AMOUNT_NOT_VALID":
                    return "退款金额不能大于支付金额";
                default:
                    return "系统错误";
            }
        } else {
            return "未知错误";
        }
    }

}

package com.d2c.order.model.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 第三方接口单据
 */
public interface IPaymentInterface extends Serializable {

    /**
     * 账单ID
     */
    Long getBillSourceId();

    /**
     * 账单编号
     */
    String getBillSourceSn();

    /**
     * 创建时间
     */
    Date getBillSourceTime();

    /**
     * 订单类型
     */
    String getBillSourceType();

    /**
     * 业务说明
     */
    String getBillSubject();

    /**
     * 业务具体信息
     */
    String getBillBody();

    /**
     * 应付费用(包含物流费用)
     */
    BigDecimal getBillTotalFee();

    /**
     * 物流费用
     */
    BigDecimal getBillShipFee();

    /**
     * 支付单ID
     */
    Long getPaymentId();

    /**
     * 用户ID
     */
    Long getMemberId();

    /**
     * 用户ID
     */
    Long getToMemberId();

    /**
     * 是否支付
     */
    boolean isWaitPay();

    /**
     * 是否跨境
     */
    boolean getCross();

    /**
     * 支付单参数集
     */
    Map<String, String> getPayParams();

}

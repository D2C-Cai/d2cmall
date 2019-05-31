package com.d2c.logger.service;

import com.d2c.logger.model.RefundLog;

import java.util.List;

/**
 * 退款单日志（refundLog）
 */
public interface RefundLogService {

    /**
     * 根据退款单id获取所有相关日志列表
     *
     * @param refundId 退款单id
     * @return
     */
    List<RefundLog> findByRefundId(Long refundId);

    /**
     * 保存退款单日志
     *
     * @param entity
     * @return
     */
    RefundLog insert(RefundLog entity);

    /**
     * 根据id获取单条退款单日志
     *
     * @param key
     * @return
     */
    RefundLog findById(Long key);

    List<RefundLog> findByOrderId(Long orderId);

    List<RefundLog> findByOrderItemId(Long orderItemId);

}

package com.d2c.logger.service;

import com.d2c.logger.model.OrderLog;

import java.util.List;

/**
 * 订单日志（orderlog）
 */
public interface OrderLogService {

    /**
     * 根据订单id获取该订单的日志
     *
     * @param orderId 订单id
     * @return
     */
    List<OrderLog> findByOrderId(Long orderId);

    /**
     * 保存订单日志
     *
     * @param entity
     * @return
     */
    OrderLog insert(OrderLog entity);

}

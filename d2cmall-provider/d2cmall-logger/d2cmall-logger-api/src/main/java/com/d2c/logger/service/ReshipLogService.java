package com.d2c.logger.service;

import com.d2c.logger.model.ReshipLog;

import java.util.List;

/**
 * 退货单日志（reshipLog）
 */
public interface ReshipLogService {

    /**
     * 根据退货单ID获取所有相关日志列表
     *
     * @param reshipId 退货单ID
     * @return
     */
    List<ReshipLog> findByReshipId(Long reshipId);

    /**
     * 保存退货单日志
     *
     * @param entity
     * @return
     */
    ReshipLog insert(ReshipLog entity);

    /**
     * 根据id获取单条退货单日志
     *
     * @param id
     * @return
     */
    ReshipLog findById(Long id);

    List<ReshipLog> findByOrderId(Long orderId);

    List<ReshipLog> findByOrderItemId(Long orderItemId);

}

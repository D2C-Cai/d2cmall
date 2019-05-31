package com.d2c.logger.service;

import com.d2c.logger.model.ExchangeLog;

import java.util.List;

public interface ExchangeLogService {

    /**
     * 根据id获取换货单日志实例
     *
     * @param key
     * @return
     */
    ExchangeLog findById(Long key);

    /**
     * 根据退货单id，获取所有相关的换货单日志
     *
     * @param exchangeId 退货单ID
     * @return
     */
    List<ExchangeLog> findByExchangeId(Long exchangeId);

    /**
     * 将换货单日志插入exchangeLog表中
     *
     * @param entity
     * @return
     */
    ExchangeLog insert(ExchangeLog entity);

}

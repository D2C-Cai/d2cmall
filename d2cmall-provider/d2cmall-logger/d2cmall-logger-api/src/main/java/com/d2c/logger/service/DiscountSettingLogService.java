package com.d2c.logger.service;

import com.d2c.logger.model.DiscountSettingLog;

public interface DiscountSettingLogService {

    /**
     * 插入一条日志记录
     *
     * @param log
     * @return
     */
    DiscountSettingLog insert(DiscountSettingLog log);

}

package com.d2c.logger.service;

import com.d2c.logger.model.SkuOperateLog;

public interface SkuOperateLogService {

    /**
     * 添加一天账户日志记录
     *
     * @param accountLog
     * @return
     */
    SkuOperateLog insert(SkuOperateLog skuOperateLog);

}

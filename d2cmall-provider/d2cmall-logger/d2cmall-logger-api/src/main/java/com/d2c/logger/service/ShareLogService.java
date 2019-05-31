package com.d2c.logger.service;

import com.d2c.logger.model.ShareLog;

public interface ShareLogService {

    /**
     * 分享插入日志表
     *
     * @param shareLog
     * @return
     */
    ShareLog insert(ShareLog shareLog);

}

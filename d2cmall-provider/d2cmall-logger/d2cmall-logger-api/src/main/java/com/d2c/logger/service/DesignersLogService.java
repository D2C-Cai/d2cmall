package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.DesignersLog;

public interface DesignersLogService {

    /**
     * 插入操作日志
     *
     * @param log
     * @return
     */
    DesignersLog insert(DesignersLog log);

    /**
     * 分页查找
     *
     * @param designersId
     * @param page
     * @return
     */
    PageResult<DesignersLog> findByDesignersId(Long designersId, PageModel page);

}

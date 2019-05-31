package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.CompensationLog;

public interface CompensationLogService {

    CompensationLog insert(CompensationLog compensationLog);

    /**
     * 分页查找设计师端操作日志
     *
     * @param id
     * @param page
     * @return
     */
    PageResult<CompensationLog> findCompensation(Long id, PageModel page);

    /**
     * 分页查找客户端操作日志
     *
     * @param id
     * @param page
     * @return
     */
    PageResult<CompensationLog> findCusCompensation(Long id, PageModel page);

}

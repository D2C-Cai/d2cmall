package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.query.ErrorLogSearcher;

public interface ErrorLogService {

    ErrorLog insert(ErrorLog errorLog);

    PageResult<ErrorLog> findBySearcher(PageModel pager, ErrorLogSearcher searcher);

    int delete(Long id);

}

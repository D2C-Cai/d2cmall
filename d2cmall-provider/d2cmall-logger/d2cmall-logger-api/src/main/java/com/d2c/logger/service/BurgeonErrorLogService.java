package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.BurgeonErrorLog;
import com.d2c.logger.query.BurgeonErrorLogSearcher;

public interface BurgeonErrorLogService {

    BurgeonErrorLog findById(Long id);

    PageResult<BurgeonErrorLog> findBySearch(BurgeonErrorLogSearcher search, PageModel page);

    int doHandle(Long id, String handleMan, String handleContent);

    BurgeonErrorLog insert(BurgeonErrorLog burgeonErrorLog);

    int update(BurgeonErrorLog burgeonErrorLog);

    int doReProcessSucess(Long id, String operator);

}

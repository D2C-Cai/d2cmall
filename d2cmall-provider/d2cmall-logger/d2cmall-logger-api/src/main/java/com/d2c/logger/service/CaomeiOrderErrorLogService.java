package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.CaomeiOrderErrorLog;

public interface CaomeiOrderErrorLogService {

    CaomeiOrderErrorLog insert(CaomeiOrderErrorLog log);

    int doSuccess(Long orderItemId);

    PageResult<CaomeiOrderErrorLog> findBySearcher(PageModel page);

}

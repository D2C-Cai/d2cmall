package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.KaolaOrderErrorLog;

public interface KaolaOrderErrorLogService {

    KaolaOrderErrorLog insert(KaolaOrderErrorLog kaolaOrderErrorLog);

    PageResult<KaolaOrderErrorLog> findBySearcher(PageModel page);

}

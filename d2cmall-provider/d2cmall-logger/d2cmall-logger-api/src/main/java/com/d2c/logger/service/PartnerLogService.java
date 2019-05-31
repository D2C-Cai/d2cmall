package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.PartnerLog;

public interface PartnerLogService {

    PartnerLog insert(PartnerLog log);

    PageResult<PartnerLog> findByPartnerId(Long partnerId, PageModel page);

}

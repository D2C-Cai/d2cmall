package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ProductReportLog;

public interface ProductReportLogService {

    /**
     * 插入操作日志
     *
     * @param log
     * @return
     */
    ProductReportLog insert(ProductReportLog log);

    /**
     * 根据商品报告id查询日志
     *
     * @return
     */
    PageResult<ProductReportLog> findByReportId(Long reportId, PageModel page);

}

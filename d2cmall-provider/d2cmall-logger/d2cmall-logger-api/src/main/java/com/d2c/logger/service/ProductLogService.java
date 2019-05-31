package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ProductLog;

public interface ProductLogService {

    ProductLog insert(ProductLog log);

    PageResult<ProductLog> findByProductId(Long productId, PageModel page);

}

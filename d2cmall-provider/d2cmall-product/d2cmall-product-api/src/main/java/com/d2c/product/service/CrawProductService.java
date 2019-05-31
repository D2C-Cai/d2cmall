package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.CrawProductDto;
import com.d2c.product.model.CrawProduct;
import com.d2c.product.query.CrawProductSearcher;

public interface CrawProductService {

    CrawProduct insert(CrawProduct crawProduct);

    PageResult<CrawProductDto> findBySearch(CrawProductSearcher searcher, PageModel page);

    void doClearProductByDesignerId(Long designerId);

}

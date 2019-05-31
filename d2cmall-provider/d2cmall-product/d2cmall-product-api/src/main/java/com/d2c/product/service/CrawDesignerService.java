package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.CrawDesigner;
import com.d2c.product.query.CrawDesignerSearcher;

public interface CrawDesignerService {

    CrawDesigner insert(CrawDesigner crawDesigner);

    CrawDesigner findById(Long id);

    int doCrawByIds(Long[] ids);

    PageResult<CrawDesigner> findBySearch(CrawDesignerSearcher searcher, PageModel page);

}

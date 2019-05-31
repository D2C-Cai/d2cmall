package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.GuanyiStock;

public interface GuanyiStockService {

    GuanyiStock insert(GuanyiStock guanyiStock);

    GuanyiStock findLast();

    GuanyiStock findFirst();

    PageResult<GuanyiStock> findBySearcher(PageModel page);

    int countBySearcher();

    int doClean();

}

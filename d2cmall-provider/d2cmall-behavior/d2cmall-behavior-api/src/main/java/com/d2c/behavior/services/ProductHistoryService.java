package com.d2c.behavior.services;

import com.d2c.behavior.mongo.model.ProductHistoryDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;

public interface ProductHistoryService {

    public PageResult<?> findHistoryDays(Long memberId, PageModel page);

    public long countHistoryDays(Long memberId);

    public ProductHistoryDO addProductHistory(Long memberId, Long productId);

    public long deleteHistoryById(String historyId);

    public long deleteAllHistory(Long memberId);

}

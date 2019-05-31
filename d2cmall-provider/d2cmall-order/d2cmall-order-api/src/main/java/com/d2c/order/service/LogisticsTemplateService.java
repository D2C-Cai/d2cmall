package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.LogisticsTemplateDto;
import com.d2c.order.query.LogisticsTemplateSearcher;

public interface LogisticsTemplateService {

    LogisticsTemplateDto insert(LogisticsTemplateDto dto);

    PageResult<LogisticsTemplateDto> findBySearcher(LogisticsTemplateSearcher searcher, PageModel page);

    int update(LogisticsTemplateDto template);

    int updateStatus(Long templateId, Integer status, String admin);

    LogisticsTemplateDto findById(Long id);

}

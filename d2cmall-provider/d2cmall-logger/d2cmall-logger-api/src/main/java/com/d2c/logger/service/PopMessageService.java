package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.PopMessage;
import com.d2c.logger.query.PopMessageSearcher;

public interface PopMessageService {

    PopMessage insert(PopMessage popMessage);

    PageResult<PopMessage> findBySearcher(PopMessageSearcher searcher, PageModel page);

    int doPush(Long id, String sendMan);

    PopMessage findById(Long id);

    int update(PopMessage popMessage);

}

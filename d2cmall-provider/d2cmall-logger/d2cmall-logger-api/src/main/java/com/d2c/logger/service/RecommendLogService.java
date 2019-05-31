package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.RecommendLog;
import com.d2c.logger.query.RecommendLogSearcher;

/**
 * 推荐日志（recommend_log）
 */
public interface RecommendLogService {

    /**
     * 保存推荐日志
     *
     * @param recommendLog
     * @return
     */
    RecommendLog insert(RecommendLog recommendLog);

    /**
     * 分页查找， 查询出的是返利金额大于0的
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<RecommendLog> findBySearcher(RecommendLogSearcher searcher, PageModel page);

}

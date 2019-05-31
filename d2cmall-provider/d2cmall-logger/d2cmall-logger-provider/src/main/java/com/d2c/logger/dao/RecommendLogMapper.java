package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.RecommendLog;
import com.d2c.logger.query.RecommendLogSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecommendLogMapper extends SuperMapper<RecommendLog> {

    List<RecommendLog> findBySearcher(@Param("searcher") RecommendLogSearcher searcher, @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") RecommendLogSearcher searcher);

}

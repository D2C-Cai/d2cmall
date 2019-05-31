package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.query.ErrorLogSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ErrorLogMapper extends SuperMapper<ErrorLog> {

    List<ErrorLog> findBySearcher(@Param("searcher") ErrorLogSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") ErrorLogSearcher searcher);

}

package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.BurgeonErrorLog;
import com.d2c.logger.query.BurgeonErrorLogSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BurgeonErrorLogMapper extends SuperMapper<BurgeonErrorLog> {

    List<BurgeonErrorLog> findBySearch(@Param("searcher") BurgeonErrorLogSearcher search,
                                       @Param("page") PageModel page);

    int countBySearch(@Param("searcher") BurgeonErrorLogSearcher search);

    int doHandle(@Param("id") Long id, @Param("handleMan") String handleMan,
                 @Param("handleContent") String handleContent);

}

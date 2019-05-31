package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.PopMessage;
import com.d2c.logger.query.PopMessageSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PopMessageMapper extends SuperMapper<PopMessage> {

    Integer countBySearcher(@Param("searcher") PopMessageSearcher searcher);

    List<PopMessage> findBySearcher(@Param("searcher") PopMessageSearcher searcher, @Param("page") PageModel page);

    int doPush(@Param("id") Long id, @Param("sendMan") String sendMan);

}

package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageDefSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDefMapper extends SuperMapper<MessageDef> {

    List<MessageDef> findBySearch(@Param("pager") PageModel page, @Param("searcher") MessageDefSearcher searcher);

    int countBySearch(@Param("searcher") MessageDefSearcher searcher);

    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    int updateDelayTime(@Param("id") Long id, @Param("timestamp") Long timestamp);

}
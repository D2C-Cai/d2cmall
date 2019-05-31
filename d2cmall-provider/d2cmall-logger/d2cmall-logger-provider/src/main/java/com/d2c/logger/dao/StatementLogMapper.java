package com.d2c.logger.dao;

import com.d2c.logger.model.StatementLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatementLogMapper extends SuperMapper<StatementLog> {

    List<StatementLog> findByStatementId(@Param("id") Long id, @Param("logType") String logType);

}

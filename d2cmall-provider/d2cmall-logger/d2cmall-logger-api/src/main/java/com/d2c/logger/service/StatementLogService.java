package com.d2c.logger.service;

import com.d2c.logger.model.StatementLog;

import java.util.List;

public interface StatementLogService {

    StatementLog insert(StatementLog statementLog);

    List<StatementLog> findByStatementId(Long id, String logType);

}

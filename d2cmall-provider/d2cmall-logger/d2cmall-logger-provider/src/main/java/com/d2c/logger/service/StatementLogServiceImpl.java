package com.d2c.logger.service;

import com.d2c.logger.dao.StatementLogMapper;
import com.d2c.logger.model.StatementLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("statementLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StatementLogServiceImpl extends ListServiceImpl<StatementLog> implements StatementLogService {

    @Autowired
    private StatementLogMapper statementLogMapper;

    @Override
    public StatementLog insert(StatementLog statementLog) {
        return this.save(statementLog);
    }

    @Override
    public List<StatementLog> findByStatementId(Long id, String logType) {
        return statementLogMapper.findByStatementId(id, logType);
    }

}

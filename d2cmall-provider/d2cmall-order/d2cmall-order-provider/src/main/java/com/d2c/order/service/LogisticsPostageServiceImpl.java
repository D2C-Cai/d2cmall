package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.LogisticsPostageMapper;
import com.d2c.order.model.LogisticsPostage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("logisticsPostageService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class LogisticsPostageServiceImpl extends ListServiceImpl<LogisticsPostage> implements LogisticsPostageService {

    @Autowired
    private LogisticsPostageMapper logisticsPostageMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertBatch(List<LogisticsPostage> logisticsPostages, Long id) {
        return logisticsPostageMapper.insertBatch(logisticsPostages, id);
    }

    @Override
    public List<LogisticsPostage> findByTemplateId(Long id) {
        return logisticsPostageMapper.findByTemplateId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(LogisticsPostage logisticsPostage) {
        return this.updateNotNull(logisticsPostage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return logisticsPostageMapper.deleteById(id);
    }

    @Override
    public LogisticsPostage insert(LogisticsPostage postage, Long tempId) {
        postage.setTemplateId(tempId);
        return this.save(postage);
    }

}

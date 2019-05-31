package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.CompensationLogMapper;
import com.d2c.logger.model.CompensationLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "compensationLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CompensationLogServiceImpl extends ListServiceImpl<CompensationLog> implements CompensationLogService {

    @Autowired
    public CompensationLogMapper compensationLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public CompensationLog insert(CompensationLog compensationLog) {
        return this.save(compensationLog);
    }

    @Override
    public PageResult<CompensationLog> findCompensation(Long id, PageModel page) {
        int totalCount = compensationLogMapper.countCompensation(id);
        PageResult<CompensationLog> pager = new PageResult<>(page);
        if (totalCount > 0) {
            List<CompensationLog> list = compensationLogMapper.findCompensation(id, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<CompensationLog> findCusCompensation(Long id, PageModel page) {
        int totalCount = compensationLogMapper.countCusCompensation(id);
        PageResult<CompensationLog> pager = new PageResult<>(page);
        if (totalCount > 0) {
            List<CompensationLog> list = compensationLogMapper.findCusCompensation(id, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

}

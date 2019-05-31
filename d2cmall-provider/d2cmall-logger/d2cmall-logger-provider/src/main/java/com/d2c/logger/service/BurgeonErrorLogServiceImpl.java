package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.BurgeonErrorLogMapper;
import com.d2c.logger.model.BurgeonErrorLog;
import com.d2c.logger.query.BurgeonErrorLogSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("burgeonErrorLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BurgeonErrorLogServiceImpl extends ListServiceImpl<BurgeonErrorLog> implements BurgeonErrorLogService {

    @Autowired
    private BurgeonErrorLogMapper burgeonErrorLogMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BurgeonErrorLog insert(BurgeonErrorLog burgeonErrorLog) {
        return this.save(burgeonErrorLog);
    }

    @Override
    public PageResult<BurgeonErrorLog> findBySearch(BurgeonErrorLogSearcher search, PageModel page) {
        PageResult<BurgeonErrorLog> pager = new PageResult<BurgeonErrorLog>(page);
        int totalCount = burgeonErrorLogMapper.countBySearch(search);
        List<BurgeonErrorLog> list = new ArrayList<BurgeonErrorLog>();
        if (totalCount > 0) {
            list = burgeonErrorLogMapper.findBySearch(search, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doHandle(Long id, String handleMan, String handleContent) {
        return burgeonErrorLogMapper.doHandle(id, handleMan, handleContent);
    }

    @Override
    public BurgeonErrorLog findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BurgeonErrorLog burgeonErrorLog) {
        return this.updateNotNull(burgeonErrorLog);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReProcessSucess(Long id, String operator) {
        return burgeonErrorLogMapper.doHandle(id, operator, "系统重新做单成功。");
    }

}

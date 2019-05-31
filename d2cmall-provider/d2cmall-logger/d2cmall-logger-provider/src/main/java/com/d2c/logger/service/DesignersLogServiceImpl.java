package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.DesignersLogMapper;
import com.d2c.logger.model.DesignersLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("designersLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DesignersLogServiceImpl extends ListServiceImpl<DesignersLog> implements DesignersLogService {

    @Autowired
    private DesignersLogMapper designersLogMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public DesignersLog insert(DesignersLog log) {
        return this.save(log);
    }

    @Override
    public PageResult<DesignersLog> findByDesignersId(Long designersId, PageModel page) {
        PageResult<DesignersLog> pager = new PageResult<DesignersLog>(page);
        int totalCount = designersLogMapper.countByDesignersId(designersId);
        if (totalCount > 0) {
            List<DesignersLog> list = designersLogMapper.findByDesignersId(designersId, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

}

package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.CaomeiOrderErrorLogMapper;
import com.d2c.logger.model.CaomeiOrderErrorLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("caomeiOrderErrorLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CaomeiOrderErrorLogServiceImpl extends ListServiceImpl<CaomeiOrderErrorLog>
        implements CaomeiOrderErrorLogService {

    @Autowired
    private CaomeiOrderErrorLogMapper caomeiOrderErrorLogMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CaomeiOrderErrorLog insert(CaomeiOrderErrorLog log) {
        return this.save(log);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccess(Long orderItemId) {
        return caomeiOrderErrorLogMapper.doSuccess(orderItemId);
    }

    @Override
    public PageResult<CaomeiOrderErrorLog> findBySearcher(PageModel page) {
        PageResult<CaomeiOrderErrorLog> pager = new PageResult<CaomeiOrderErrorLog>(page);
        int totalCount = caomeiOrderErrorLogMapper.countBySearcher();
        if (totalCount > 0) {
            List<CaomeiOrderErrorLog> list = caomeiOrderErrorLogMapper.findBySearcher(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

}

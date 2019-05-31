package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.KaolaOrderErrorLogMapper;
import com.d2c.logger.model.KaolaOrderErrorLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("kaolaOrderErrorLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class KaolaOrderErrorLogServiceImpl extends ListServiceImpl<KaolaOrderErrorLog>
        implements KaolaOrderErrorLogService {

    @Autowired
    private KaolaOrderErrorLogMapper kaolaOrderErrorLogMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public KaolaOrderErrorLog insert(KaolaOrderErrorLog kaolaOrderErrorLog) {
        return this.save(kaolaOrderErrorLog);
    }

    @Override
    public PageResult<KaolaOrderErrorLog> findBySearcher(PageModel page) {
        PageResult<KaolaOrderErrorLog> pager = new PageResult<KaolaOrderErrorLog>(page);
        int totalCount = kaolaOrderErrorLogMapper.countBySearcher();
        if (totalCount > 0) {
            List<KaolaOrderErrorLog> list = kaolaOrderErrorLogMapper.findBySearcher(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

}

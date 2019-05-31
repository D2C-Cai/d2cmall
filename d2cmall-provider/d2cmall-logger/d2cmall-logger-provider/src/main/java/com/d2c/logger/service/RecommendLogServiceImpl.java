package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.RecommendLogMapper;
import com.d2c.logger.model.RecommendLog;
import com.d2c.logger.query.RecommendLogSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("recommendLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RecommendLogServiceImpl extends ListServiceImpl<RecommendLog> implements RecommendLogService {

    @Autowired
    private RecommendLogMapper recommendLogMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RecommendLog insert(RecommendLog recommendLog) {
        return this.save(recommendLog);
    }

    @Override
    public PageResult<RecommendLog> findBySearcher(RecommendLogSearcher searcher, PageModel page) {
        int totalCount = recommendLogMapper.countBySearcher(searcher);
        PageResult<RecommendLog> pager = new PageResult<RecommendLog>(page);
        List<RecommendLog> list = new ArrayList<RecommendLog>();
        if (totalCount > 0) {
            list = recommendLogMapper.findBySearcher(searcher, page);
            pager.setTotalCount(totalCount);
        }
        pager.setList(list);
        return pager;
    }

}

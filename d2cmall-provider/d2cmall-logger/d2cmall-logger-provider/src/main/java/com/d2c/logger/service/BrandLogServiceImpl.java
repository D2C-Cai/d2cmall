package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.BrandLogMapper;
import com.d2c.logger.model.BrandLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("brandLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BrandLogServiceImpl extends ListServiceImpl<BrandLog> implements BrandLogService {

    @Autowired
    private BrandLogMapper brandLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public BrandLog insert(BrandLog log) {
        return this.save(log);
    }

    @Override
    public PageResult<BrandLog> findByDesignerId(Long designerId, PageModel page) {
        PageResult<BrandLog> pager = new PageResult<BrandLog>(page);
        int totalCount = brandLogMapper.countByDesignerId(designerId);
        List<BrandLog> list = new ArrayList<BrandLog>();
        if (totalCount > 0) {
            list = brandLogMapper.findByDesignerId(designerId, page);
            pager.setTotalCount(totalCount);
        }
        pager.setList(list);
        return pager;
    }

}

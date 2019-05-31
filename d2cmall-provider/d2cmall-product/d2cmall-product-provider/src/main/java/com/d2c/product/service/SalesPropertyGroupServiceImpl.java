package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.SalesPropertyGroupMapper;
import com.d2c.product.model.SalesPropertyGroup;
import com.d2c.product.query.SalesPropertyGroupSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("salesPropertyGroupService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesPropertyGroupServiceImpl extends ListServiceImpl<SalesPropertyGroup>
        implements SalesPropertyGroupService {

    @Autowired
    private SalesPropertyGroupMapper salesPropertyGroupMapper;

    public SalesPropertyGroup findById(Long id) {
        return super.findOneById(id);
    }

    public PageResult<SalesPropertyGroup> findBySearch(SalesPropertyGroupSearcher searcher, PageModel page) {
        PageResult<SalesPropertyGroup> pager = new PageResult<SalesPropertyGroup>(page);
        int totalCount = salesPropertyGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<SalesPropertyGroup> list = salesPropertyGroupMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public SalesPropertyGroup insert(SalesPropertyGroup salesPropertyGroup) {
        return save(salesPropertyGroup);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SalesPropertyGroup salesPropertyGroup) {
        return this.updateNotNull(salesPropertyGroup);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return salesPropertyGroupMapper.updateStatus(id, status);
    }

}

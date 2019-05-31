package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.CrmGroupMapper;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("crmGroupService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CrmGroupServiceImpl extends ListServiceImpl<CrmGroup> implements CrmGroupService {

    @Autowired
    private CrmGroupMapper crmGroupMapper;

    @Override
    public CrmGroup findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public CrmGroup findByName(String name) {
        return crmGroupMapper.findByName(name);
    }

    @Override
    public PageResult<CrmGroup> findBySearcher(PageModel page, CrmGroupSearcher searcher) {
        PageResult<CrmGroup> pager = new PageResult<CrmGroup>(page);
        Integer toltalCount = crmGroupMapper.countBySearcher(searcher);
        List<CrmGroup> list = new ArrayList<CrmGroup>();
        if (toltalCount > 0) {
            list = crmGroupMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(toltalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CrmGroup insert(CrmGroup crmGroup) {
        return this.save(crmGroup);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(CrmGroup crmGroup) {
        return this.updateNotNull(crmGroup);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return crmGroupMapper.updateStatus(id, status);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return crmGroupMapper.updateSort(id, sort);
    }

}

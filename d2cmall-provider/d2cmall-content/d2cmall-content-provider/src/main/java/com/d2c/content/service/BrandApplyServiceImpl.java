package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.BrandApplyMapper;
import com.d2c.content.model.BrandApply;
import com.d2c.content.query.BrandApplySearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("brandApplyService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BrandApplyServiceImpl extends ListServiceImpl<BrandApply> implements BrandApplyService {

    @Autowired
    private BrandApplyMapper brandApplyMapper;

    public BrandApply findById(Long id) {
        return brandApplyMapper.selectByPrimaryKey(id);
    }

    public BrandApply findByMemberId(Long memberId) {
        return brandApplyMapper.findByMemberId(memberId);
    }

    public PageResult<BrandApply> findBySearcher(BrandApplySearcher searcher, PageModel page) {
        PageResult<BrandApply> pager = new PageResult<BrandApply>(page);
        int totalCount = brandApplyMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<BrandApply> list = brandApplyMapper.findBySearcher(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public BrandApply insert(BrandApply apply) {
        return this.save(apply);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(BrandApply brandApply) {
        return this.updateNotNull(brandApply);
    }

}

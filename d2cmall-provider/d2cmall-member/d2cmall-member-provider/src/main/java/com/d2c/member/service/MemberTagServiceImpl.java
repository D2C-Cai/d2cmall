package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberTagMapper;
import com.d2c.member.model.MemberTag;
import com.d2c.member.query.MemberTagSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberTagService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberTagServiceImpl extends ListServiceImpl<MemberTag> implements MemberTagService {

    @Autowired
    private MemberTagMapper memberTagMapper;
    @Autowired
    private MemberTagRelationService memberTagRelationService;

    @Override
    public MemberTag findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<MemberTag> findBySearch(PageModel page, MemberTagSearcher searcher) {
        PageResult<MemberTag> pager = new PageResult<>(page);
        Integer totalCount = memberTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberTag> list = memberTagMapper.findBySearch(page, searcher);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public Integer countBySearch(MemberTagSearcher searcher) {
        return memberTagMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberTag insert(MemberTag memberTag) {
        return this.save(memberTag);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        int success = memberTagMapper.deleteById(id);
        if (success > 0) {
            memberTagRelationService.deleteByTagId(id);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        int success = memberTagMapper.updateStatus(id, status);
        if (success > 0) {
            memberTagRelationService.updateStatusByTagId(id, status);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(MemberTag memberTag) {
        return this.updateNotNull(memberTag);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return memberTagMapper.updateSort(id, sort);
    }

}

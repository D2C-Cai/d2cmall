package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberShareTagMapper;
import com.d2c.member.model.MemberShareTag;
import com.d2c.member.query.MemberShareTagSearcher;
import com.d2c.member.search.model.SearcherMemberShareTag;
import com.d2c.member.search.service.MemberShareTagSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberShareTagService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberShareTagServiceImpl extends ListServiceImpl<MemberShareTag> implements MemberShareTagService {

    @Autowired
    private MemberShareTagMapper memberShareTagMapper;
    @Reference
    private MemberShareTagSearcherService memberShareTagSearcherService;

    @Override
    public MemberShareTag findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public MemberShareTag findByCode(String code) {
        return memberShareTagMapper.findByCode(code);
    }

    @Override
    public List<MemberShareTag> findByMemberShareId(Long shareId) {
        return memberShareTagMapper.findByMemberShareId(shareId);
    }

    @Override
    public List<MemberShareTag> findAll() {
        return memberShareTagMapper.findAll();
    }

    @Override
    public PageResult<MemberShareTag> findBySearch(MemberShareTagSearcher searcher, PageModel page) {
        PageResult<MemberShareTag> pager = new PageResult<>(page);
        int totalCount = memberShareTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberShareTag> list = memberShareTagMapper.findBySearch(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public int countBySearch(MemberShareTagSearcher searcher) {
        return memberShareTagMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberShareTag insert(MemberShareTag memberShareTag) {
        memberShareTag = this.save(memberShareTag);
        SearcherMemberShareTag smt = new SearcherMemberShareTag();
        BeanUtils.copyProperties(memberShareTag, smt);
        memberShareTagSearcherService.insert(smt);
        return memberShareTag;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        deleteById(id);
        memberShareTagSearcherService.remove(id);
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(MemberShareTag memberShareTag) {
        this.updateNotNull(memberShareTag);
        memberShareTag = memberShareTagMapper.selectByPrimaryKey(memberShareTag.getId());
        SearcherMemberShareTag smt = new SearcherMemberShareTag();
        BeanUtils.copyProperties(memberShareTag, smt);
        memberShareTagSearcherService.update(smt);
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        int result = memberShareTagMapper.updateSort(id, sort);
        if (result > 0) {
            SearcherMemberShareTag sms = new SearcherMemberShareTag();
            sms.setId(id);
            sms.setSort(sort);
            memberShareTagSearcherService.update(sms);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        int result = memberShareTagMapper.updateStatus(id, status);
        if (result > 0) {
            if (status == 1) {
                MemberShareTag memberShareTag = memberShareTagMapper.selectByPrimaryKey(id);
                SearcherMemberShareTag smt = new SearcherMemberShareTag();
                BeanUtils.copyProperties(memberShareTag, smt);
                memberShareTagSearcherService.insert(smt);
            } else {
                memberShareTagSearcherService.remove(id);
            }
        }
        return result;
    }

}

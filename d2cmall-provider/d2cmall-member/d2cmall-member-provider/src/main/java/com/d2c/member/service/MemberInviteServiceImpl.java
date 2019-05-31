package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberInviteMapper;
import com.d2c.member.model.MemberInvite;
import com.d2c.member.query.MemberInviteSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("memberInviteService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberInviteServiceImpl extends ListServiceImpl<MemberInvite> implements MemberInviteService {

    @Autowired
    private MemberInviteMapper memberInviteMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberInvite insert(MemberInvite memberPrivilegeApply) {
        return this.save(memberPrivilegeApply);
    }

    @Override
    public PageResult<MemberInvite> findBySearcher(MemberInviteSearcher searcher, PageModel page) {
        PageResult<MemberInvite> pager = new PageResult<MemberInvite>(page);
        int totalCount = memberInviteMapper.countBySearcher(searcher);
        List<MemberInvite> list = new ArrayList<MemberInvite>();
        if (totalCount > 0) {
            list = memberInviteMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefuse(Long id, String refuseReason, String lastModifyMan) {
        return memberInviteMapper.doRefuse(id, refuseReason, lastModifyMan);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAgree(Long id, String lastModifyMan) {
        return memberInviteMapper.doAgree(id, lastModifyMan);
    }

}

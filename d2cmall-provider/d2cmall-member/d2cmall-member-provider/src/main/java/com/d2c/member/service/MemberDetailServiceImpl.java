package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberDetailMapper;
import com.d2c.member.model.MemberDetail;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("memberDetailService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberDetailServiceImpl extends ListServiceImpl<MemberDetail> implements MemberDetailService {

    @Autowired
    private MemberDetailMapper memberDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public MemberDetail insert(MemberDetail memberDetail) {
        return this.save(memberDetail);
    }

    @Override
    @CacheEvict(value = "member_detail", key = "'member_detail_'+#memberId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateByMemberId(Long memberId, MemberDetail memberDertail) {
        return memberDetailMapper.updateByMemberId(memberId, memberDertail);
    }

    @Override
    @Cacheable(value = "member_detail", key = "'member_detail_'+#memberInfoId", unless = "#result == null")
    public MemberDetail findByMemberInfoId(Long memberInfoId) {
        return memberDetailMapper.findByMemberInfoId(memberInfoId);
    }

    @Override
    @CacheEvict(value = "member_detail", key = "'member_detail_'+#memberInfoId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateLevel(Integer additionalAmount, Integer level, Date upgradeDate, Long memberInfoId) {
        return memberDetailMapper.updateLevel(additionalAmount, level, upgradeDate, memberInfoId);
    }

    @Override
    public PageResult<MemberDetail> findExpireMember(PageModel page) {
        PageResult<MemberDetail> pager = new PageResult<>(page);
        int totalCount = memberDetailMapper.countExpireMember();
        if (totalCount > 0) {
            List<MemberDetail> list = memberDetailMapper.findExpireMember(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countExpireMember() {
        return memberDetailMapper.countExpireMember();
    }

    @Override
    @TxTransaction
    @CacheEvict(value = "member_detail", key = "'member_detail_'+#memberId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateIntegration(Long memberId, Integer point) {
        int success = memberDetailMapper.updateIntegration(memberId, point);
        return success;
    }

}

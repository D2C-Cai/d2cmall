package com.d2c.member.service;

import com.d2c.member.dao.MagazineMemberMapper;
import com.d2c.member.model.MagazineMember;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("magazineMemberService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MagazineMemberServiceImpl extends ListServiceImpl<MagazineMember> implements MagazineMemberService {

    @Autowired
    private MagazineMemberMapper magazineMemberMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MagazineMember insert(MagazineMember magazineMember) {
        return this.save(magazineMember);
    }

    @Override
    public MagazineMember findOne(Long memberId, String code) {
        return magazineMemberMapper.findOne(memberId, code);
    }

}

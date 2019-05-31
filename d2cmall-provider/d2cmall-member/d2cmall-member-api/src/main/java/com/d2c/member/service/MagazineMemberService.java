package com.d2c.member.service;

import com.d2c.member.model.MagazineMember;

public interface MagazineMemberService {

    /**
     * 插入一条记录
     *
     * @param magazineMember
     * @return
     */
    MagazineMember insert(MagazineMember magazineMember);

    /**
     * 查询历史
     *
     * @param memberId
     * @param code
     * @return
     */
    MagazineMember findOne(Long memberId, String code);

}

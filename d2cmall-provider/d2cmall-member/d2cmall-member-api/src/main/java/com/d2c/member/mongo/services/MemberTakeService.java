package com.d2c.member.mongo.services;

import com.d2c.member.mongo.model.MemberTakeDO;

import java.util.List;

public interface MemberTakeService {

    int insert(MemberTakeDO awardMemberDO);

    List<MemberTakeDO> findByMemberIdAndType(Long memberId, String type);

}

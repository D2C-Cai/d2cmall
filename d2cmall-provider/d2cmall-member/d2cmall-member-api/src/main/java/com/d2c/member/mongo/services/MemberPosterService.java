package com.d2c.member.mongo.services;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.mongo.model.MemberPosterDO;

public interface MemberPosterService {

    MemberPosterDO insert(MemberPosterDO memberPosterDO);

    MemberPosterDO findById(String id);

    MemberPosterDO findByMemberId(String memberId);

    PageResult<MemberPosterDO> findTops(PageModel page);

    MemberPosterDO updateLikeCount(String id, Integer count);

    MemberPosterDO updateDissCount(String id, Integer count);

    int countRanking(Integer count);

}

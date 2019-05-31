package com.d2c.member.mongo.services;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.mongo.model.MemberPosterLikeDO;

import java.util.Date;

public interface MemberPosterLikeService {

    MemberPosterLikeDO insert(MemberPosterLikeDO memberPosterLikeDO);

    int countByDate(String memberId, String postId, Date startDate, Date endDate);

    PageResult<MemberPosterLikeDO> findByPostId(String postId, PageModel page);

}

package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberDailySign;
import com.d2c.member.query.MemberDailySignSearcher;

public interface MemberDailySignService {

    /**
     * 签到
     *
     * @param memberId
     * @param loginCode
     * @return
     */
    MemberDailySign doSign(Long memberId, String loginCode);

    /**
     * 按条件查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberDailySign> findBySearcher(MemberDailySignSearcher searcher, PageModel page);

    /**
     * 查找最近一条签到记录
     *
     * @param memberId
     * @return
     */
    MemberDailySign findLastByMember(Long memberId);

}

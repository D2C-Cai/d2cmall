package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberDetail;

import java.util.Date;

public interface MemberDetailService {

    /**
     * 新增会员信息
     *
     * @param memberDetail
     * @return
     */
    MemberDetail insert(MemberDetail memberDetail);

    /**
     * 根据memberinfoId查询
     *
     * @param memberId
     * @return
     */
    MemberDetail findByMemberInfoId(Long memberId);

    /**
     * 根据memberinfoId更新
     *
     * @param memberId
     * @param memberDertail
     * @return
     */
    int updateByMemberId(Long memberId, MemberDetail memberDertail);

    /**
     * 更新等级
     *
     * @param additionalAmount
     * @param level
     * @param date
     * @param memberInfoId
     * @return
     */
    int updateLevel(Integer additionalAmount, Integer level, Date date, Long memberInfoId);

    /**
     * 查找等级满365天用户
     *
     * @param page
     * @return
     */
    PageResult<MemberDetail> findExpireMember(PageModel page);

    /**
     * 查找等级满365天用户数量
     *
     * @return
     */
    int countExpireMember();

    /**
     * 更新积分
     *
     * @param memberId
     * @param point
     * @return
     */
    int updateIntegration(Long memberId, Integer point);

}

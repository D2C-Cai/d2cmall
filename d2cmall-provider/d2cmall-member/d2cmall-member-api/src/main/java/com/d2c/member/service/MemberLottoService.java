package com.d2c.member.service;

import com.d2c.member.model.MemberLotto;

public interface MemberLottoService {

    /**
     * 新增抽奖机会(根据会员的操作行为)
     *
     * @param memberDto
     * @param lotteryOpportunityEnum
     * @return
     */
    int updateMemberLottery(Long memberId, MemberLotto.LotteryOpportunityEnum lotteryOpportunityEnum, Long sessionId);

    /**
     * 成功后减1次抽奖机会
     *
     * @param memberId
     * @return
     */
    int doDecreaseOpportunity(Long memberId, Long sessionId);

    /**
     * 查询抽奖权限
     *
     * @param memberId
     * @param session
     * @return
     */
    MemberLotto findByMemberIdAndSessionId(Long memberId, Long sessionId);

    /**
     * 清空抽奖次数和记录
     *
     * @return
     */
    int doClear();

}

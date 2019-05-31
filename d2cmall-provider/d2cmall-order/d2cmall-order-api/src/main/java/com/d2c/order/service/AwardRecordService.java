package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberLotto;
import com.d2c.order.dto.AwardRecordDto;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.query.AwardRecordSearcher;

import java.util.List;

public interface AwardRecordService {

    /**
     * 查询中奖的抽奖列表(按照大小权值分配)
     *
     * @return
     */
    List<AwardRecord> findListByRecently(Long sessionId);

    /**
     * 按会员ID查询
     *
     * @param memberId
     * @return
     */
    List<AwardRecord> findByMemberIdAndSessionId(Long memberId, Long sessionId);

    /**
     * 输入查询条件.返回奖品记录
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AwardRecordDto> findBySearcher(AwardRecordSearcher searcher, PageModel page);

    /**
     * 输入条件返回记录数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(AwardRecordSearcher searcher);

    /**
     * 记录抽奖结果
     *
     * @param awardRecord
     * @return
     */
    AwardRecord insert(AwardRecord awardRecord);

    /**
     * 开始抽奖
     *
     * @param member
     * @return 抽奖结果
     */
    AwardRecord doStartLottery(MemberLotto member);

    /**
     * @return
     */
    List<String> findAwardLevelName();

}

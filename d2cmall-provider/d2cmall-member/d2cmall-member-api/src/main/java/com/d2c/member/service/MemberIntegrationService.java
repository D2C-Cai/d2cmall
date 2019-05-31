package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.model.MemberTask;
import com.d2c.member.query.MemberIntegrationSearcher;

import java.math.BigDecimal;

public interface MemberIntegrationService {

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberIntegration> findBySearch(MemberIntegrationSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(MemberIntegrationSearcher searcher);

    /**
     * 查询已经兑换数量
     *
     * @param memberId
     * @param productId
     * @return
     */
    int countByExchange(Long memberId, Long productId);

    /**
     * 新增明细
     *
     * @param memberIntegration
     * @return
     */
    MemberIntegration insert(MemberIntegration memberIntegration);

    /**
     * 积分变换
     *
     * @param memberIntegration
     * @param type
     * @param amount
     * @param point
     * @param transactionInfo
     * @return
     */
    int addIntegration(MemberIntegration memberIntegration, PointRuleTypeEnum type, BigDecimal amount, Integer point,
                       String transactionInfo);

    /**
     * 用户日常任务增加积分
     *
     * @param memberId
     * @param task
     * @return
     */
    MemberIntegration addTaskPoint(Long memberId, MemberTask task);

}

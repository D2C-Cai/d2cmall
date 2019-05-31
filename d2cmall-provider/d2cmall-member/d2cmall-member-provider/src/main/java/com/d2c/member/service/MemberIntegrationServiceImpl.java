package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.member.dao.MemberIntegrationMapper;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.model.MemberTask;
import com.d2c.member.query.MemberIntegrationSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("memberIntegrationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberIntegrationServiceImpl extends ListServiceImpl<MemberIntegration>
        implements MemberIntegrationService {

    @Autowired
    public IntegrationRuleService integrationRuleService;
    @Autowired
    private MemberIntegrationMapper memberIntegrationMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberDetailService memberDetailService;

    @Override
    public PageResult<MemberIntegration> findBySearch(MemberIntegrationSearcher searcher, PageModel page) {
        PageResult<MemberIntegration> pager = new PageResult<>(page);
        int totalCount = memberIntegrationMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberIntegration> list = memberIntegrationMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(MemberIntegrationSearcher searcher) {
        return memberIntegrationMapper.countBySearch(searcher);
    }

    @Override
    public int countByExchange(Long memberId, Long productId) {
        return memberIntegrationMapper.countByExchange(memberId, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public MemberIntegration insert(MemberIntegration memberIntegration) {
        return this.save(memberIntegration);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int addIntegration(MemberIntegration memberIntegration, PointRuleTypeEnum type, BigDecimal amount,
                              Integer point, String transactionInfo) {
        IntegrationRule rule = integrationRuleService.findVaildByType(type.name());
        if (rule != null) {
            memberIntegration.setPoint(type.calculatePoint(rule.getRatio(), amount));
            memberIntegration.setTransactionInfo(rule.getName());
        } else {
            memberIntegration.setPoint(point);
            memberIntegration.setTransactionInfo(transactionInfo);
        }
        memberIntegration.setType(type.name());
        memberIntegration = this.insert(memberIntegration);
        int success = memberDetailService.updateIntegration(memberIntegration.getMemberId(),
                memberIntegration.getPoint() * memberIntegration.getDirection());
        return success;
    }

    /**
     * 用户日常任务增加积分
     */
    public MemberIntegration addTaskPoint(Long memberId, MemberTask task) {
        AssertUt.notNull(memberId);
        AssertUt.notNull(task);
        MemberInfo member = memberInfoService.findById(memberId);
        AssertUt.notNull(member);
        MemberIntegration bean = new MemberIntegration(member, PointRuleTypeEnum.TASK, task.getPoint());
        bean.setTransactionId(task.getId());
        bean.setTransactionSn(task.getCode());
        bean.setTransactionTime(new Date());
        bean.setTransactionInfo("用户完成任务:" + task.getName());
        this.saveIntegration(bean);
        return bean;
    }

    private MemberIntegration saveIntegration(MemberIntegration bean) {
        this.insert(bean);
        memberDetailService.updateIntegration(bean.getMemberId(), bean.getPoint() * bean.getDirection());
        return bean;
    }

}

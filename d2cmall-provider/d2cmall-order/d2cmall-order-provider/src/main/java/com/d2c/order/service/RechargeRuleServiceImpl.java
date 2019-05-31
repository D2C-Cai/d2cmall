package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RechargeRuleMapper;
import com.d2c.order.dto.RechargeRuleDto;
import com.d2c.order.model.RechargeRule;
import com.d2c.order.query.RechargeRuleSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("rechargeRuleService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RechargeRuleServiceImpl extends ListServiceImpl<RechargeRule> implements RechargeRuleService {

    @Autowired
    private RechargeRuleMapper rechargeRuleMapper;

    @Override
    public RechargeRule findById(Long id) {
        return rechargeRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public RechargeRuleDto findValidRule() {
        RechargeRule rule = rechargeRuleMapper.findValidRule();
        RechargeRuleDto dto = new RechargeRuleDto();
        if (rule != null) {
            BeanUtils.copyProperties(rule, dto);
        }
        return dto;
    }

    @Override
    public PageResult<RechargeRuleDto> findBySearch(RechargeRuleSearcher searcher, PageModel page) {
        PageResult<RechargeRuleDto> result = new PageResult<>(page);
        int totalCount = rechargeRuleMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<RechargeRule> values = rechargeRuleMapper.findBySearch(searcher, page);
            List<RechargeRuleDto> dtos = this.doCopyProperties(values);
            result.setTotalCount(totalCount);
            result.setList(dtos);
        }
        return result;
    }

    private List<RechargeRuleDto> doCopyProperties(List<RechargeRule> rules) {
        List<RechargeRuleDto> dtos = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            RechargeRuleDto dto = new RechargeRuleDto();
            RechargeRule rule = rules.get(i);
            BeanUtils.copyProperties(rule, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RechargeRule insert(RechargeRule rule, String username) {
        rechargeRuleMapper.insert(rule);
        // 定时上下架
        if (rule.getTiming() == 1) {
            this.timingRuleMQ(rule, username, 1, rule.getStartTime());
        }
        return rule;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(RechargeRule rule, String username) {
        RechargeRule old = this.findOneById(rule.getId());
        if (rule.getStatus() > 0) {
            throw new BusinessException("上架状态，修改不成功");
        }
        int result = rechargeRuleMapper.updateByPrimaryKey(rule);
        if (rule.getTiming() == 1) {
            // 发送消息
            if (!(old.getStartTime().equals(rule.getStartTime()))) {
                this.timingRuleMQ(rule, username, 1, rule.getStartTime());
            }
        }
        return result;
    }

    private void timingRuleMQ(RechargeRule rule, String username, int status, Date date) {
        Date now = new Date();
        if (rule.getEndTime().after(now)) {
            long interval = (date.getTime() - now.getTime()) / 1000;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", rule.getId());
            map.put("status", status);
            map.put("time", date.getTime());
            map.put("username", username);
            MqEnum.TIMING_RULE.send(map, interval);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMark(Long ruleId, String username, Integer status) {
        RechargeRule rule = this.findOneById(ruleId);
        int success = 0;
        if (status == 1) {
            // 下架其他的，过期的不管
            rechargeRuleMapper.doOverRule();
        }
        success = rechargeRuleMapper.doMark(ruleId, username, status);
        if (success > 0) {
            // 只要是上架的，过期都下架
            if (status == 1) {
                this.timingRuleMQ(rule, username, 0, rule.getEndTime());
            }
        }
        return success;
    }

}

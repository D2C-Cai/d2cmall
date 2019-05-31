package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.RechargeRuleDto;
import com.d2c.order.model.RechargeRule;
import com.d2c.order.query.RechargeRuleSearcher;

public interface RechargeRuleService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RechargeRule findById(Long id);

    /**
     * 查询有效的规则
     *
     * @return
     */
    RechargeRuleDto findValidRule();

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<RechargeRuleDto> findBySearch(RechargeRuleSearcher searcher, PageModel page);

    /**
     * 插入新规则
     *
     * @param rule
     * @param username
     * @return
     */
    RechargeRule insert(RechargeRule rule, String username);

    /**
     * 更新规则
     *
     * @param rule
     * @param username
     * @return
     */
    int update(RechargeRule rule, String username);

    /**
     * 上下架
     *
     * @param ruleId
     * @param username
     * @param status
     * @return
     */
    int doMark(Long ruleId, String username, Integer status);

}

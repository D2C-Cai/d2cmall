package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.query.IntegrationRuleSearcher;

public interface IntegrationRuleService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    IntegrationRule findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<IntegrationRule> findBySearch(IntegrationRuleSearcher searcher, PageModel page);

    /**
     * 根据searcher数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(IntegrationRuleSearcher searcher);

    /**
     * 新增
     *
     * @param integrationRule
     * @return
     */
    IntegrationRule insert(IntegrationRule integrationRule);

    /**
     * 更新
     *
     * @param integrationRule
     * @return
     */
    int update(IntegrationRule integrationRule);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @param userName
     * @return
     */
    int updateStatus(Long id, Integer status, String userName);

    /**
     * 根据类型和收支查找上架状态的积分规则
     *
     * @param type
     * @return
     */
    IntegrationRule findVaildByType(String type);

}

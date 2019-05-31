package com.d2c.product.service;

import com.d2c.product.model.BargainRule;

import java.math.BigDecimal;
import java.util.List;

public interface BargainRuleService {

    /**
     * 添加
     *
     * @param bargainRule
     * @return
     */
    BargainRule insert(BargainRule bargainRule);

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据promotionId查询规则
     *
     * @param promotionId
     * @return
     */
    List<BargainRule> findByPromotionId(Long promotionId);

    /**
     * 更新
     *
     * @param rule
     * @return
     */
    int update(BargainRule rule);

    /**
     * 查找低于@{@code price}的{@code promotionId}活动规则的最大金额
     *
     * @param promotionId
     * @param price
     * @return
     */
    BargainRule findUpperRule(Long promotionId, BigDecimal price);

    /**
     * 查找最先一级
     *
     * @param bargainId
     * @return
     */
    BargainRule findFirst(Long bargainId);

    /**
     * 查找最后一级
     *
     * @param bargainId
     * @return
     */
    BargainRule findLast(Long bargainId);

}

package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.entity.RecomRuleDO;

import java.util.List;

/**
 * 推荐规则表
 *
 * @author wull
 */
public interface RecomRuleService extends ListService<RecomRuleDO> {

    public List<RecomRuleDO> findAllRules();

    public List<RecomRuleDO> rebuildRules();

}

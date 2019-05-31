package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.dto.SimilarRuleDTO;
import com.d2c.similar.entity.SimilarRuleDO;

import java.util.List;

/**
 * 相似度规则
 *
 * @author wull
 */
public interface SimilarRuleService extends ListService<SimilarRuleDO> {

    public SimilarRuleDTO getSimilarRuleOnEdit(Integer ruleId);

    public List<SimilarRuleDO> createRuleBySchemeId(Integer schemeId);

    public SimilarRuleDO findRuleByCode(Integer schemeId, String ruleCode);

    public List<SimilarRuleDO> findRulesBySchemeId(Integer schemeId);

}

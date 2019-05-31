package com.d2c.similar.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.similar.entity.SimilarRuleDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wull
 */
public interface SimilarRuleMapper extends SuperMapper<SimilarRuleDO> {

    public SimilarRuleDO findRuleByCode(@Param("schemeId") Integer schemeId, @Param("ruleCode") String ruleCode);

}

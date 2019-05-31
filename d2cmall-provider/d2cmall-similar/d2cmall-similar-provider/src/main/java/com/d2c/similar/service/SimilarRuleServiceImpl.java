package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.similar.dao.SimilarRuleMapper;
import com.d2c.similar.dto.SimilarRuleDTO;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.enums.SimilarRuleEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wull
 */
@Service(protocol = "dubbo")
public class SimilarRuleServiceImpl extends ListServiceImpl<SimilarRuleDO> implements SimilarRuleService {

    @Autowired
    private SimilarRuleMapper mapper;

    public SimilarRuleDTO getSimilarRuleOnEdit(Integer ruleId) {
        SimilarRuleDTO bean = convert(findOneById(ruleId));
//		bean.setDimenKeys(dimenKeyService.findDimenKeys(ruleId));
//		bean.setDimenTpls(dimenTplService.findDimenTplByRuleCode(bean.getRuleCode()));
        return bean;
    }

    /**
     * 创建默认规则
     */
    public List<SimilarRuleDO> createRuleBySchemeId(Integer schemeId) {
        List<SimilarRuleDO> list = findRulesBySchemeId(schemeId);
        if (list.isEmpty()) {
            list = SimilarRuleEnum.getAllRules();
            for (SimilarRuleDO rule : list) {
                rule.setSchemeId(schemeId);
                save(rule);
            }
        }
        return list;
    }

    @Override
    public SimilarRuleDO findRuleByCode(Integer schemeId, String ruleCode) {
        return mapper.findRuleByCode(schemeId, ruleCode);
    }

    @Override
    public List<SimilarRuleDO> findRulesBySchemeId(Integer schemeId) {
        return findByFieldName("schemeId", schemeId);
    }

    private SimilarRuleDTO convert(SimilarRuleDO bean) {
        return ConvertUt.convertBean(bean, SimilarRuleDTO.class);
    }

}

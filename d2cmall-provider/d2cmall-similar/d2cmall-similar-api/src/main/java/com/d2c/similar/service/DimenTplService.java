package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.entity.DimenTplDO;

import java.util.List;

/**
 * 商品属性维度
 *
 * @author wull
 */
public interface DimenTplService extends ListService<DimenTplDO> {

    public List<DimenTplDO> findDimenTplByRuleCode(String ruleCode);

}

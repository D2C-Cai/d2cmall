package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.entity.DimenKeyDO;

import java.util.List;

/**
 * 商品属性维度
 *
 * @author wull
 */
public interface DimenKeyService extends ListService<DimenKeyDO> {

    public List<DimenKeyDO> findDimenKeys(Integer ruleId);

    public DimenKeyDO createDimenKey(Integer ruleId, String fieldName, String fieldValue);

}

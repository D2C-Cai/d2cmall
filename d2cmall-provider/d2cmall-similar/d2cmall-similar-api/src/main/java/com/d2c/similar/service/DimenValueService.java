package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.entity.DimenValueDO;

import java.util.List;

/**
 * 商品属性维度
 *
 * @author wull
 */
public interface DimenValueService extends ListService<DimenValueDO> {

    public List<DimenValueDO> findDimenValueByKeyId(Integer keyId);

    public List<Double> getDistByValue(Integer ruleId, String fieldValue);

    public DimenValueDO createDimenValue(Integer keyId, Integer tplId, Double dist);

}

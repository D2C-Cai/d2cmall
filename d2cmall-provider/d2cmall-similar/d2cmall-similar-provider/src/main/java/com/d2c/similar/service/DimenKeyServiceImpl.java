package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.similar.entity.DimenKeyDO;

import java.util.List;

/**
 * @author wull
 */
@Service(protocol = "dubbo")
public class DimenKeyServiceImpl extends ListServiceImpl<DimenKeyDO> implements DimenKeyService {

    public List<DimenKeyDO> findDimenKeys(Integer ruleId) {
        return this.findByFieldName("ruleId", ruleId);
    }

    public DimenKeyDO createDimenKey(Integer ruleId, String fieldName, String fieldValue) {
        return save(new DimenKeyDO(ruleId, fieldName, fieldValue));
    }

}

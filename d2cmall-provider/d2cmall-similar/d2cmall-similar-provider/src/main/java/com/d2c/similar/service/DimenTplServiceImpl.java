package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.similar.entity.DimenTplDO;
import com.d2c.similar.enums.DimenTplEnum;

import java.util.List;

/**
 * @author wull
 */
@Service(protocol = "dubbo")
public class DimenTplServiceImpl extends ListServiceImpl<DimenTplDO> implements DimenTplService {

    @Override
    public List<DimenTplDO> findDimenTplByRuleCode(String ruleCode) {
        List<DimenTplDO> list = findByFieldName("ruleCode", ruleCode);
        if (list.isEmpty()) {
            list = createDefault(ruleCode);
        }
        return list;
    }

    /**
     * 创建默认模板
     */
    private List<DimenTplDO> createDefault(String ruleCode) {
        return saveAll(DimenTplEnum.findDimenTplByCode(ruleCode));
    }

}

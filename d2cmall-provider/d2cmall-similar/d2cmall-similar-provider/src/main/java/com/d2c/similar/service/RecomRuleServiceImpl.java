package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.similar.entity.RecomRuleDO;
import com.d2c.similar.enums.RecomRuleEnum;

import java.util.List;

/**
 * 商品推荐表
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class RecomRuleServiceImpl extends ListServiceImpl<RecomRuleDO> implements RecomRuleService {

    /**
     * 重建规则
     */
    public List<RecomRuleDO> findAllRules() {
        List<RecomRuleDO> list = findAll();
        if (list.isEmpty()) {
            list = createDefaultRules();
        }
        return list;
    }

    /**
     * 重建规则
     */
    public List<RecomRuleDO> rebuildRules() {
        deleteAll();
        return createDefaultRules();
    }

    /**
     * 创建默认规则
     */
    private List<RecomRuleDO> createDefaultRules() {
        return saveAll(RecomRuleEnum.getAllRules());
    }

}

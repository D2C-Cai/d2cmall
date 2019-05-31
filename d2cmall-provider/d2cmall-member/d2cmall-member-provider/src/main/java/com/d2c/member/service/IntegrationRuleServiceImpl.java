package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.IntegrationRuleMapper;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.query.IntegrationRuleSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("integrationRuleService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class IntegrationRuleServiceImpl extends ListServiceImpl<IntegrationRule> implements IntegrationRuleService {

    @Autowired
    private IntegrationRuleMapper integrationRuleMapper;

    @Override
    public IntegrationRule findById(Long id) {
        return integrationRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<IntegrationRule> findBySearch(IntegrationRuleSearcher searcher, PageModel page) {
        PageResult<IntegrationRule> pager = new PageResult<>(page);
        int totalCount = integrationRuleMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<IntegrationRule> list = integrationRuleMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(IntegrationRuleSearcher searcher) {
        return integrationRuleMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public IntegrationRule insert(IntegrationRule integrationRule) {
        return this.save(integrationRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(IntegrationRule integrationRule) {
        return this.updateNotNull(integrationRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String userName) {
        int success = integrationRuleMapper.updateStatus(id, status);
        if (success > 0) {
            // TODO 生成日志
        }
        return success;
    }

    @Override
    public IntegrationRule findVaildByType(String type) {
        return integrationRuleMapper.findVaildByType(type);
    }

}

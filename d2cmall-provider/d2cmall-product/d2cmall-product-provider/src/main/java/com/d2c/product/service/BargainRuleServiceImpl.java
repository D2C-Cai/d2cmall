package com.d2c.product.service;

import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BargainRuleMapper;
import com.d2c.product.model.BargainRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("bargainRuleService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BargainRuleServiceImpl extends ListServiceImpl<BargainRule> implements BargainRuleService {

    @Autowired
    private BargainRuleMapper bargainRuleMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BargainRule insert(BargainRule bargainRule) {
        int totalCount = bargainRuleMapper.countByPromotion(bargainRule.getBargainPromotionId());
        if (totalCount >= 5) {
            throw new BusinessException("砍价规则上限为5个，已经超过上限了！");
        }
        return this.save(bargainRule);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return deleteById(id);
    }

    @Override
    public List<BargainRule> findByPromotionId(Long promotionId) {
        return bargainRuleMapper.findByPromotionId(promotionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BargainRule rule) {
        return this.updateNotNull(rule);
    }

    @Override
    public BargainRule findUpperRule(Long promotionId, BigDecimal price) {
        return bargainRuleMapper.findUpperRule(promotionId, price);
    }

    @Override
    public BargainRule findFirst(Long bargainId) {
        return bargainRuleMapper.findFirst(bargainId);
    }

    @Override
    public BargainRule findLast(Long bargainId) {
        return bargainRuleMapper.findLast(bargainId);
    }

}

package com.d2c.similar.similar.resolver;

import com.d2c.common.core.base.resolver.BaseResolverFactory;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.similar.resolver.enums.SimilarRuleType;
import org.springframework.stereotype.Component;

@Component
public class SimilarResolverFactory extends BaseResolverFactory<SimilarResolver, SimilarRuleDO> {

    @Override
    public SimilarResolver initResolver(SimilarResolver prev, SimilarRuleDO rule) {
        return SimilarRuleType.createResolver(rule);
    }

}

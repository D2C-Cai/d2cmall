package com.d2c.similar.recom.resolver;

import com.d2c.common.core.base.resolver.BaseResolverFactory;
import com.d2c.similar.entity.RecomRuleDO;
import org.springframework.stereotype.Component;

@Component
public class RecomResolverFactory extends BaseResolverFactory<RecomResolver, RecomRuleDO> {

    @Override
    public RecomResolver initResolver(RecomResolver prev, RecomRuleDO rule) {
        return new RecomResolver();
    }

}

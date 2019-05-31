package com.d2c.similar.similar.resolver.arith;

import com.d2c.common.base.utils.ConvertUt;

/**
 * 正态分布比率算法
 *
 * @author wull
 */
public class NormalRateArith extends NormalDistrArith {

    /**
     * 和原值的比率作为数据
     */
    @Override
    protected double getDeviation() {
        Double val = ConvertUt.convertType(bean, Double.class);
        return val * rule.getDeviation();
    }

}

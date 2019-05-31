package com.d2c.similar.similar.resolver.enums;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.similar.resolver.arith.AbstractDistrArith;
import com.d2c.similar.similar.resolver.arith.NormalDistrArith;
import com.d2c.similar.similar.resolver.arith.NormalRateArith;
import com.d2c.similar.similar.resolver.arith.SimpleLineArith;

public enum ArithType {
    SIMPLE_LINE_ARITH(SimpleLineArith.class, "简单线性算法"),
    NORMAL_ARITH(NormalDistrArith.class, "数值正态分布"),
    NORMAL_RATE_ARITH(NormalRateArith.class, "数值比率正态分布");
    Class<? extends AbstractDistrArith> clz;
    String remark;

    ArithType(Class<? extends AbstractDistrArith> clz, String remark) {
        this.clz = clz;
        this.remark = remark;
    }

    public AbstractDistrArith create(SimilarRuleDO rule) {
        AbstractDistrArith arith = BeanUt.newInstance(this.clz);
        arith.init(rule);
        return arith;
    }

    @Override
    public String toString() {
        return this.remark;
    }
}

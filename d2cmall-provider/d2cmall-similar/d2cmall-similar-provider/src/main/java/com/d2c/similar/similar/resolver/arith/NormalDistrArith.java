package com.d2c.similar.similar.resolver.arith;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * 正态分布算法
 *
 * @author wull
 */
public class NormalDistrArith extends AbstractDistrArith {

    private NormalDistribution nd;
    private double maxProb;

    public NormalDistrArith() {
        nd = new NormalDistribution();
        maxProb = nd.density(0);
    }

    @Override
    public double doProb() {
        return nd.density(dist / deviation) / maxProb;
    }

}

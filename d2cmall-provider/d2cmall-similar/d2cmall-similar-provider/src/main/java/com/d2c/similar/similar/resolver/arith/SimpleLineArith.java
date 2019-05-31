package com.d2c.similar.similar.resolver.arith;

/**
 * 简单线性算法
 * prob = 1 - dist/deviation
 * <p>
 * 相似度随着距离从 0 - deviation 线性下降
 * dist = 0 时 prob = 1;
 * dist = deviation 时 prob = 0;
 *
 * @author wull
 */
public class SimpleLineArith extends AbstractDistrArith {

    @Override
    public double doProb() {
        if (deviation < 0) {
            deviation = 1;
        }
        return 1 - (dist / deviation);
    }

}

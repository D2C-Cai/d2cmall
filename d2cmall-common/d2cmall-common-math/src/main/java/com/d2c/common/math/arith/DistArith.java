package com.d2c.common.math.arith;

import com.d2c.common.base.exception.AssertException;

/**
 * 距离算法
 * <p>计算两者之间的距离
 *
 * @author wull
 */
public class DistArith {

    /**
     * 直线距离
     *
     * @param value  数值
     * @param target 目标数值
     * @return 距离
     */
    public static double lineDist(double value, double target) {
        return Math.abs(value - target);
    }

    /**
     * 两个对象是否相等
     * <p>相等距离为0， 不相等距离为1
     *
     * @param value  数值
     * @param target 目标数值
     * @return 距离
     */
    public static double equalsDist(Object value, Object target) {
        if (value == null) {
            return target == null ? 1 : 0;
        }
        return value.equals(target) ? 1 : 0;
    }

    /**
     * 欧式距离 (欧几里得距离)
     * <p> 公式: 如三维 0ρ = √( (x1-x2)^2+(y1-y2)^2+(z1-z2)^2 )
     * <br>Math.sqrt(Math.pow((a - b),2) + ...)
     * <p>指在m维空间中两个点之间的真实距离
     * <br>用途: 主要用于离散型数据[1,0.5,4,0]与[1,2,1,3]，变换为连续数值距离
     *
     * @param value  数值
     * @param target 目标数值
     * @return 距离
     */
    public static double euclidDist(double[] values, double[] targets) {
        if (values.length != targets.length) {
            throw new AssertException("维度数量不一致，请检查.. (" + values + ", " + targets + ")");
        }
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i] - targets[i], 2);
        }
        return Math.sqrt(sum);
    }

}

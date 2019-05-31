package com.d2c.similar.similar.resolver.dist;

import com.d2c.similar.similar.resolver.enums.SimilarFieldType;

/**
 * 相似度距离算法
 *
 * @author wull
 */
public class BasicDistance extends AbstractDistance {

    @Override
    public double doExec() {
        double v = getFieldValue(value);
        double t = getFieldValue(target);
        return Math.abs(v - t);
    }

    private double getFieldValue(Object value) {
        return SimilarFieldType.valueOf(rule.getFieldType()).getFieldValue(value);
    }

}

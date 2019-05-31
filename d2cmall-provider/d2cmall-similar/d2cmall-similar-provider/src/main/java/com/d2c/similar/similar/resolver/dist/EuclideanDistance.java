package com.d2c.similar.similar.resolver.dist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 欧式距离, 体现数据差异系
 * <p>
 * Math.sqrt(Math.pow((a - b),2) + ...)
 * <p>
 * （余弦相似度：数据不敏感，更多体现不同用户评价兴趣相似度，因为用户可能存在量度不统一问题)
 */
public class EuclideanDistance extends AbstractDimenDistance {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public double doDimenProb() {
        if (this.valueList.size() != this.targetList.size()) {
            logger.error("比较维度数量不一致，请检查.. (" + valueList.size() + ", " + targetList.size() + ")");
            return 1;
        }
        double sum = 0;
        for (int i = 0; i < valueList.size(); i++) {
            sum += Math.pow(valueList.get(i) - targetList.get(i), 2);
        }
        return Math.sqrt(sum);
    }

}

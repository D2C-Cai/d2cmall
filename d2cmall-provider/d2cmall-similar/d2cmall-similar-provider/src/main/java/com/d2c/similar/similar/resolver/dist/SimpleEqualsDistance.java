package com.d2c.similar.similar.resolver.dist;

/**
 * 简单相等即相似
 * 相等距离为0，否则距离为1
 *
 * @author wull
 */
public class SimpleEqualsDistance extends AbstractDistance {

    @Override
    public double doExec() {
        if (value.equals(target)) {
            return 0;
        }
        return 1;
    }

}

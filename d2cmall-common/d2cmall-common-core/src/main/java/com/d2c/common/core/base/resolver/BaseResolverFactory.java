package com.d2c.common.core.base.resolver;

import java.util.List;

public abstract class BaseResolverFactory<T extends BaseResolver<R>, R> {

    /**
     * 初始化解析责任链
     */
    public T initResolver(List<R> list) {
        if (list == null) return null;
        T first = null;
        T prev = null;
        T rsv = null;
        for (R rule : list) {
            rsv = initResolver(prev, rule);
            rsv.init(prev, rule);
            if (first == null) {
                first = rsv;
            }
            prev = rsv;
        }
        return first;
    }

    protected abstract T initResolver(T prev, R rule);

}

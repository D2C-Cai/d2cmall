package com.d2c.common.core.base.resolver;

/**
 * 基础解析器
 *
 * @author wull
 */
public abstract class BaseResolver<T> {

    protected BaseResolver<T> next;
    protected T rule;

    /**
     * 计算数据相似度
     */
    protected void resolver() {
        doResolver();
        next();
    }

    /**
     * 执行下一个计算
     */
    protected void next() {
        if (next != null) {
            doNext();
            next.resolver();
        }
    }

    protected abstract void doResolver();

    protected abstract void doNext();

    protected abstract void doInit();
    // ********************** init **************************

    /**
     * 初始化
     */
    public void init(BaseResolver<T> prev, T rule) {
        this.rule = rule;
        doInit();
        prevResolver(prev);
    }

    /**
     * 构建链条，上一个
     */
    public void prevResolver(BaseResolver<T> prev) {
        if (prev != null) {
            prev.nextResolver(this);
        }
    }

    /**
     * 构建链条，下一个
     */
    public void nextResolver(BaseResolver<T> next) {
        this.next = next;
    }

}

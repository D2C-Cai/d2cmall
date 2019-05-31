package com.d2c.common.core.tools;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 固定大小的 LinkedHashMap
 * <p>put数据判断个数是否超过maxSize，超过删掉最老的数据
 *
 * @author wull
 */
public class LimitHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = -8352872496007137051L;
    private static final long MAX_SIZE = 10000;
    private long maxSize;

    public LimitHashMap() {
        this(MAX_SIZE);
    }

    public LimitHashMap(long max) {
        super();
        this.maxSize = max;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

}

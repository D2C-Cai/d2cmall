package com.d2c.common.core.base.bucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * 分页桶
 * <p>轮询数据，按分页获取
 *
 * @author wull
 */
public abstract class PageBucket<T> implements Iterable<T>, Iterator<T> {

    private static final int BUCKET_PAGE_SIZE = 500;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected int pageSize = BUCKET_PAGE_SIZE;
    protected int page = 0, count = 0;
    protected Integer maxSize;
    protected List<T> list;
    protected ListIterator<T> it;
    protected boolean debug = false;

    public PageBucket() {
        init();
    }

    public PageBucket(Integer maxSize) {
        this.maxSize = maxSize;
        init();
    }

    public PageBucket(Integer maxSize, int pageSize) {
        this.maxSize = maxSize;
        this.pageSize = pageSize;
        init();
    }

    public void init() {
    }

    public PageBucket<T> reset() {
        page = 0;
        count = 0;
        it = null;
        return this;
    }

    public boolean hasNext() {
        if (maxSize != null && maxSize > 0 && count >= maxSize) {
            return false;
        }
        if (it == null || !it.hasNext()) {
            page = page + 1;
            if (debug) {
                logger.info("开始查询..." + this);
            }
            list = nextList(page, pageSize);
            it = list.listIterator();
            if (!it.hasNext()) {
                page--;
                return false;
            }
        }
        return true;
    }

    @Override
    public T next() {
        count++;
        return it.next();
    }

    public List<T> getNextList() {
        count += list.size();
        it = null;
        return list;
    }

    public List<T> getAllList() {
        List<T> list = null;
        while (true) {
            List<T> nextList = nextList(page++, pageSize);
            if (list == null) {
                list = nextList;
            } else {
                list.addAll(nextList);
            }
            count = list.size();
            if (nextList.size() < pageSize) {
                break;
            }
            if (maxSize != null && maxSize > 0 && count >= maxSize) {
                break;
            }
        }
        return list;
    }

    public void setStartPage(int startPage) {
        if (startPage <= 1) {
            page = 0;
        } else {
            page = startPage - 1;
        }
        count = page * pageSize;
        it = null;
    }

    public void setStart(int start) {
        setStartPage((start / pageSize) + 1);
    }

    @Override
    public String toString() {
        return " count: " + count + " page: " + page + " pageSize: " + pageSize;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    public abstract List<T> nextList(int page, int pageSize);

    @Override
    public void forEach(Consumer<? super T> action) {
        forEachRemaining(action);
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}

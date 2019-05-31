package com.d2c.common.api.page;

import com.d2c.common.api.page.base.PageSort;

import java.io.Serializable;

public interface Pager extends Serializable {

    /**
     * 页码
     */
    int getPage();

    /**
     * 每页数量
     */
    int getPageSize();

    /**
     * 开始序列
     */
    int getOffset();

    /**
     * 排序方式
     */
    PageSort getPageSort();

    void setPageSort(PageSort sort);

    /**
     * 下一页
     */
    Pager next();

    /**
     * 上一页
     */
    Pager prev();

    /**
     * 第一页
     */
    Pager first();

    /**
     * 是否有上一页
     */
    boolean hasPrev();

}

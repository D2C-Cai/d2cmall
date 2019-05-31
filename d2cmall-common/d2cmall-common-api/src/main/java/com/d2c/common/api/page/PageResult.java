package com.d2c.common.api.page;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageResult<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页记录数
     */
    private int pageSize = 20;
    /**
     * 总记录数
     */
    private int total = 0;
    /**
     * 总页数
     */
    private int pageCount = 0;
    /**
     * 数据List
     */
    private List<T> list;

    public PageResult() {
    }

    public PageResult(Page<T> resPage) {
        if (resPage != null) {
            this.list = resPage.getContent();
            this.page = resPage.getNumber() + 1;
            this.pageSize = resPage.getSize();
            this.pageCount = resPage.getTotalPages();
            this.total = (int) resPage.getTotalElements();
        }
    }

    public PageResult(Pager pager) {
        if (pager != null) {
            this.page = pager.getPage();
            this.pageSize = pager.getPageSize();
        }
    }

    public PageResult(Pager pager, List<T> list) {
        this(pager);
        setList(list);
    }

    public PageResult(Pager pager, List<T> list, Integer count) {
        this(pager, list);
        setTotalCount(count);
    }

    public PageResult(Pager pager, List<T> list, Long count) {
        this(pager, list);
        setTotalCount(count != null ? count.intValue() : null);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public long getOffset() {
        return (page - 1) * pageSize;
    }

    public long getStartRow() {
        return getOffset();
    }

    public int getPageNumber() {
        return page;
    }

    public void setPageNumber(int pageNumber) {
        this.page = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return total;
    }

    public void setTotalCount(int totalCount) {
        this.total = totalCount;
        if (pageSize == 0) {
            pageCount = 0;
        } else {
            pageCount = (totalCount + pageSize - 1) / pageSize;
        }
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isNext() {
        if (this.pageCount > this.page) {
            return true;
        }
        return false;
    }

    public boolean isForward() {
        if (this.page <= 1) {
            return false;
        }
        return true;
    }

    public int getPage() {
        return page;
    }

    public long getTotal() {
        return total;
    }

}
package com.d2c.common.api.page;

import org.springframework.data.domain.Sort;

public class PageModel extends PageBean {

    public final static int MAX_PAGE_SIZE = 1000; // 每页最大记录数限制
    private static final long serialVersionUID = -1850733368749886250L;

    public PageModel() {
        this(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public PageModel(int page, int size) {
        super(page, size);
    }

    public PageModel(boolean isAsc, String... properties) {
        super(DEFAULT_PAGE, DEFAULT_PAGE_SIZE, isAsc, properties);
    }

    public PageModel(int page, int size, boolean isAsc, String... properties) {
        super(page, size, isAsc, properties);
    }

    public PageModel(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public PageModel next() {
        page++;
        return this;
    }

    public int getP() {
        return page;
    }

    public void setP(int page) {
        setPage(page);
    }

    public int getPs() {
        return size;
    }

    public void setPs(int size) {
        setPageSize(size);
    }

    public int getPageNumber() {
        return page;
    }

    public void setPageNumber(int page) {
        this.page = page;
    }

    @Override
    public void setPageSize(int size) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        super.setPageSize(size);
    }

    public int getStartNumber() {
        return getOffset();
    }

}

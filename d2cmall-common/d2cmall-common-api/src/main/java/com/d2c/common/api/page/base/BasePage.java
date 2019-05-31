package com.d2c.common.api.page.base;

import com.d2c.common.api.page.Pager;

public abstract class BasePage implements Pager {

    public final static int DEFAULT_PAGE = 1; // 默认当前页
    public final static int DEFAULT_PAGE_SIZE = 20; // 默认每页显示记录数
    private static final long serialVersionUID = -3780970195703763972L;
    protected int page;
    protected int size;
    protected int offSet;

    public BasePage() {
        this(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public BasePage(int page, int size) {
        setPage(page);
        setPageSize(size);
    }

    public void setOffSet(int offSet) {
        if (offSet < 0) {
            offSet = (page - 1) * size;
        }
        this.offSet = offSet;
    }

    @Override
    public boolean hasPrev() {
        return page > 0;
    }

    @Override
    public Pager prev() {
        return hasPrev() ? previous() : first();
    }

    private Pager previous() {
        page--;
        return this;
    }

    @Override
    public Pager next() {
        page++;
        return this;
    }

    @Override
    public Pager first() {
        page = 1;
        return this;
    }

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            page = DEFAULT_PAGE;
        }
        this.page = page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    public void setPageSize(int size) {
        if (size <= 0) {
            size = DEFAULT_PAGE_SIZE;
        }
        this.size = size;
    }

    /**
     * 开始序列
     */
    @Override
    public int getOffset() {
        if (offSet > 0) {
            return offSet;
        }
        return (page - 1) * size;
    }
    // **************** override **********************

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + page;
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasePage other = (BasePage) obj;
        return this.page == other.page && this.size == other.size;
    }

}

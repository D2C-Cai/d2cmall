package com.d2c.common.api.page;

import com.d2c.common.api.page.base.BasePage;
import com.d2c.common.api.page.base.PageSort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class PageBean extends BasePage {

    private static final long serialVersionUID = 6087953084794597263L;
    protected PageSort pageSort;
    protected List<String> sort;
    protected String direction;

    public PageBean() {
        super();
    }

    public PageBean(int limit) {
        this(DEFAULT_PAGE, limit);
    }

    public PageBean(int page, int size) {
        super(page, size);
    }

    public PageBean(int page, int size, boolean isAsc, String... properties) {
        this(page, size, new PageSort(isAsc, properties));
    }

    public PageBean(int page, int size, Sort sort) {
        this(page, size, new PageSort(sort));
    }

    public PageBean(int page, int size, PageSort pageSort) {
        super(page, size);
        this.pageSort = pageSort;
    }

    public PageSort getPageSort() {
        buildSort();
        return pageSort;
    }

    public void setPageSort(PageSort pageSort) {
        this.pageSort = pageSort;
    }

    public void setPageSort(boolean isAsc, String... properties) {
        this.pageSort = new PageSort(isAsc, properties);
    }

    public void addSort(List<String> sort) {
        if (sort == null) {
            setSort(sort);
        } else {
            this.sort.addAll(sort);
        }
    }

    public void addSort(String... sort) {
        addSort(Arrays.asList(sort));
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public void setSort(String... sort) {
        setSort(Arrays.asList(sort));
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void buildSort() {
        if (pageSort == null) {
            if (sort == null) return;
            boolean isAsc = false;
            if (StringUtils.isNotBlank(direction)) {
                isAsc = direction.equalsIgnoreCase("asc");
            }
            pageSort = PageHelper.buildSort(pageSort, isAsc, sort.toArray(new String[]{}));
        }
    }
    //************** override ******************

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageBean)) {
            return false;
        }
        PageBean that = (PageBean) obj;
        boolean sortEqual = this.pageSort == null ? that.pageSort == null : this.pageSort.equals(that.pageSort);
        return super.equals(that) && sortEqual;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (null == pageSort ? 0 : pageSort.hashCode());
    }

    @Override
    public String toString() {
        return String.format("PageBean[page-%d, size-%d, sort-%s]", getPage(), getPageSize(),
                pageSort == null ? null : pageSort.toString());
    }

}

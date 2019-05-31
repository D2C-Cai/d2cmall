package com.d2c.common.api.page.convert;

import com.d2c.common.api.page.PageBean;
import com.d2c.common.api.page.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageConvert {

    public static PageRequest convert(int page, int size) {
        return convert(new PageBean(page, size));
    }

    public static PageRequest convert(Pager page) {
        if (page == null) {
            page = new PageBean();
        }
        Sort sort = null;
        if (page.getPageSort() != null) {
            sort = page.getPageSort().getSort();
        }
        return new PageRequest(page.getPage() - 1, page.getPageSize(), sort);
    }

}

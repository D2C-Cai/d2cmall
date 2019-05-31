package com.d2c.mybatis.page;

import com.d2c.common.api.page.Pager;
import org.apache.ibatis.session.RowBounds;

public class PageHandler {

    public final static int DEFAULT_PAGE = 1; //默认当前页
    public final static int DEFAULT_PAGE_SIZE = 20; //默认每页显示记录数

    public static RowBounds init(Pager pager) {
        return init(pager.getPage(), pager.getPageSize());
    }

    public static RowBounds init(Integer page, Integer limit) {
        if (page == null || page <= 0) {
            page = DEFAULT_PAGE;
        }
        if (limit == null || limit <= 0) {
            limit = DEFAULT_PAGE_SIZE;
        }
        return new RowBounds((page - 1) * limit, limit);
    }

}

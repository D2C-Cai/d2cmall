package com.d2c.common.mongodb.page;

import com.d2c.common.api.page.PageBean;
import com.d2c.common.api.page.Pager;
import com.d2c.common.api.page.base.PageSort;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;

public class PageHandler {

    public final static int DEFAULT_PAGE = 1; //默认当前页
    public final static int DEFAULT_PAGE_SIZE = 20; //默认每页显示记录数

    public static Query init(Query query, Integer limit) {
        if (query == null) {
            query = new Query();
        }
        if (limit == null || limit <= 0) {
            limit = DEFAULT_PAGE_SIZE;
        }
        return query.limit(limit);
    }

    public static Query init(Query query, Pager pager) {
        if (pager == null) {
            pager = new PageBean();
        }
        query = init(query, pager.getPage(), pager.getPageSize());
        PageSort pageSort = pager.getPageSort();
        if (pageSort != null) {
            query.with(pageSort.getSort());
        }
        return query;
    }

    public static Query init(Query query, Integer page, Integer limit) {
        query = init(query, limit);
        if (page != null && page > 1) {
            query.skip((page - 1) * limit);
        }
        return query;
    }

    public static Query init(Query query, Integer page, Integer limit, String orderField, boolean isAsc) {
        query = init(query, page, limit);
        query.with(new Sort(isAsc ? Direction.ASC : Direction.DESC, orderField));
        return query;
    }

}

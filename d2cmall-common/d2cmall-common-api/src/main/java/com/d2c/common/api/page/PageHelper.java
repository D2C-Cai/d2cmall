package com.d2c.common.api.page;

import com.d2c.common.api.page.base.PageSort;
import org.apache.commons.lang3.StringUtils;

public class PageHelper {

    public final static int MAX_PAGE_SIZE = 500; //每页最大记录数限制

    public static PageModel init() {
        return new PageModel();
    }

    public static PageModel init(int page, int size) {
        return new PageModel(page, size);
    }

    public static PageModel initMax() {
        return new PageModel(1, MAX_PAGE_SIZE);
    }

    /**
     * 创建排序，根据 price|asc 表示
     */
    public static PageSort buildSort(String... keys) {
        return buildSort(null, false, keys);
    }

    public static PageSort buildSort(PageSort pageSort, boolean isAsc, String... keys) {
        if (pageSort == null) {
            pageSort = new PageSort();
        }
        for (String key : keys) {
            if (StringUtils.isBlank(key)) continue;
            String[] ss = StringUtils.split(key, "|");
            if (ss.length >= 2) {
                if (ss[1].equalsIgnoreCase("asc")) {
                    pageSort.add(true, ss[0]);
                } else {
                    pageSort.add(false, ss[0]);
                }
            } else {
                pageSort.add(false, key);
            }
        }
        return pageSort;
    }

}

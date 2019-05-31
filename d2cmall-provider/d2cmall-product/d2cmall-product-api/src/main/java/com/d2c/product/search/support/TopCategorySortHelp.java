package com.d2c.product.search.support;

import java.util.HashMap;
import java.util.Map;

public class TopCategorySortHelp {

    private static Map<Long, Integer> sort;

    static {
        sort = new HashMap<Long, Integer>();
        sort.put(1L, 1);
        sort.put(6L, 2);
        sort.put(7L, 3);
        sort.put(3L, 4);
        sort.put(9L, 5);
        sort.put(13L, 6);
        sort.put(15L, 7);
        sort.put(12L, 8);
        sort.put(11L, 9);
        sort.put(14L, 10);
        sort.put(16L, 11);
    }

    public static Integer compare(Long id1, Long id2) {
        return sort.get(id1) != null && sort.get(id2) != null ? (sort.get(id1) > sort.get(id2) ? 1 : -1) : -1;
    }

}

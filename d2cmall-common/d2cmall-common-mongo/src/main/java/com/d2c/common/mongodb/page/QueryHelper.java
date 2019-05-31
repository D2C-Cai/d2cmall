package com.d2c.common.mongodb.page;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;

public class QueryHelper {

    /**
     * 数据排序
     */
    public static Query sort(Query query, boolean isAsc, String... orderFields) {
        return query.with(new Sort(isAsc ? Direction.ASC : Direction.DESC, orderFields));
    }

    /**
     * 排序查询最后一条数据
     */
    public static Query lastOne(Query query, boolean isAsc, String... orderFields) {
        return sort(query, isAsc, orderFields).limit(1);
    }

}

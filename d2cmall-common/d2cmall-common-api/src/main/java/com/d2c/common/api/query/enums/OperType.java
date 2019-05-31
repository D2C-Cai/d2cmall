package com.d2c.common.api.query.enums;

public enum OperType {
    IS_NULL,
    NOT_NULL,
    EQUAL,
    NOT_EQUAL,
    LIKE,
    NOT_LIKE,
    GT,
    LT,
    GTE,
    LTE,
    BETWEEN,
    IN,
    NOT_IN,
    //Elasticsearch
    FUZZY, //模糊搜索
    MULTI_MATCH, //多字段搜索匹配
    NULL,//手动处理
    OR,
    ORDER_BY,
    DISTINCT
}

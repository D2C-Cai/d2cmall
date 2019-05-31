package com.d2c.logger.search.service;

import com.d2c.logger.search.model.SearcherMemberSum;

import java.util.Set;

public interface SearchKeySearcherService {

    public static final String TYPE_SEACHER = "typeseacher";

    /**
     * 添加关键字搜索数据
     *
     * @param searchSum
     * @return
     */
    int insert(SearcherMemberSum searchSum, Integer mark);

    /**
     * 通过关键字，得到展示名字集合
     *
     * @param keywords
     * @return
     */
    Set<String> search(String keywords);

    /**
     * 通过关键字，移除关键字搜索数据
     *
     * @param keyword
     * @return
     */
    int removeByKey(String keyword);

    /**
     * 通过类型，资源id，删除搜索数据
     *
     * @param type
     * @param sourceId
     * @return
     */
    int removeByTypeAndSourceId(String type, String sourceId);

    /**
     * 删除所有
     */
    int removeAll();

}

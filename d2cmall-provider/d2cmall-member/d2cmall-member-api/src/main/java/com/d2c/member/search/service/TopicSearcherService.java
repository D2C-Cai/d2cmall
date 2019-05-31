package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherTopic;
import com.d2c.member.search.query.TopicSearchBean;

public interface TopicSearcherService {

    public static final String TYPE_TOPIC = "typetopic";

    /**
     * 插入搜素
     *
     * @param searcherTopic
     * @return
     */
    int insert(SearcherTopic searcherTopic);

    /**
     * 通过id，得到商品分类搜索数据
     *
     * @param id
     * @return
     */
    SearcherTopic findById(Long id);

    /**
     * 更新
     *
     * @param product
     * @return
     */
    int update(SearcherTopic topic);

    /**
     * 查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherTopic> search(TopicSearchBean searcher, PageModel page);

    /**
     * 通过id，移除搜索数据
     *
     * @param pcId
     * @return
     */
    int remove(Long id);

    /**
     * 清空索引
     */
    void removeAll();

}

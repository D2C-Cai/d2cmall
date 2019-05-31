package com.d2c.content.search.service;

import com.d2c.content.search.model.SearcherSensitiveWords;

public interface SensitiveWordsSearcherService {

    public static final String TYPE_SEACHER = "typesensitiveword";

    /**
     * 添加搜索数据
     *
     * @param ssw
     * @return
     */
    int insert(SearcherSensitiveWords ssw);

    /**
     * 更新搜索数据
     *
     * @param ssw
     * @return
     */
    int update(SearcherSensitiveWords ssw);

    /**
     * 重建搜索数据
     *
     * @param ssw
     * @return
     */
    int rebuild(SearcherSensitiveWords ssw);

    /**
     * 通过id，移除搜索数据
     *
     * @param id
     * @return
     */
    int remove(Long id);

    /**
     * 清空所有
     */
    int removeAll();

}

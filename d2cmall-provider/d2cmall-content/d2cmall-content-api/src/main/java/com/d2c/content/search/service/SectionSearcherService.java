package com.d2c.content.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.query.SectionSearchBean;

public interface SectionSearcherService {

    public static final String TYPE_SECTION = "typesection";

    /**
     * 添加一条
     *
     * @param searcherSection
     * @return
     */
    int insert(SearcherSection searcherSection);

    /**
     * 分页查询搜索数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherSection> search(SectionSearchBean searcher, PageModel page);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    int deleteBySectionDefId(String id);

    /**
     * 通过版本删除
     *
     * @param moduleId
     * @param version
     * @return
     */
    int deleteVersion(String moduleId, Integer version);

    /**
     * 重新建立咨询回复搜索记录
     *
     * @param searcherSection
     * @return
     */
    int rebuild(SearcherSection searcherSection);

    /**
     * 删除一条
     *
     * @param id
     * @return
     */
    int remove(String id);

    /**
     * 清空所有
     */
    void removeAll();

}

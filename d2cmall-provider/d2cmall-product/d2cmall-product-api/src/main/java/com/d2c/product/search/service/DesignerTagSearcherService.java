package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherDesignerTag;

public interface DesignerTagSearcherService {

    static final String TYPE_DESIGNER_TAG = "typedesignertag";

    /**
     * 添加一条设计师标签的搜索数据
     *
     * @param tag
     * @return
     */
    int insert(SearcherDesignerTag tag);

    /**
     * 通过id，得到设计师标签搜索数据
     *
     * @param id
     * @return
     */
    SearcherDesignerTag findById(String id);

    /**
     * 查询分页设计师标签搜索数据
     *
     * @param page
     * @return
     */
    PageResult<SearcherDesignerTag> search(PageModel page);

    /**
     * 更新一条设计师标签的搜索数据
     *
     * @param tag
     * @return
     */
    int update(SearcherDesignerTag tag);

    /**
     * 重新建立设计师标签的搜索数据
     *
     * @param tag
     * @return
     */
    int rebuild(SearcherDesignerTag tag);

    /**
     * 通过标签id，删除搜索数据
     *
     * @param tagId
     * @return
     */
    int remove(Long tagId);

    /**
     * 清空所有的设计师搜索的数据
     */
    void removeAll();

}

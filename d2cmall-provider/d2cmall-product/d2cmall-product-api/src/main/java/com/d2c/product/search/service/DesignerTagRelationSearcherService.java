package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherDesignerTagRelation;
import com.d2c.product.search.query.DesignerSearchBean;

public interface DesignerTagRelationSearcherService {

    public static final String TYPE_DESIGNER_TAG_R = "typedesignertagr";

    /**
     * 添加一条设计师标签关联的搜索数据
     *
     * @param relation
     * @return
     */
    int insert(SearcherDesignerTagRelation relation);

    /**
     * 通过设计师id，标签id，得到设计师关联的搜索数据
     *
     * @param designerId
     * @param tagId
     * @return
     */
    SearcherDesignerTagRelation findById(Long designerId, Long tagId);

    /**
     * 分页查询设计师关联的搜索数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherDesignerTagRelation> search(DesignerSearchBean search, PageModel page);

    /**
     * 添加一条设计师标签关联的搜索数据
     *
     * @param relation
     * @return
     */
    int updateSort(SearcherDesignerTagRelation relation);

    /**
     * 重新建立计师标签关联的搜索数据
     *
     * @param relation
     * @return
     */
    int rebuild(SearcherDesignerTagRelation relation);

    /**
     * 通过设计师id，标签id，删除搜索数据
     *
     * @param designerId
     * @param tagId
     * @return
     */
    int remove(Long designerId, Long tagId);

    /**
     * 通过设计师id，删除指定的搜索数据
     *
     * @param designerId
     * @return
     */
    int remove(Long designerId);

    /**
     * 清空所有的设计师搜索的数据
     */
    void removeAll();

    /**
     * 标签id，得到设计师关联的搜索数据
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<String> findDesignerByTagId(Long tagId, PageModel page);

}

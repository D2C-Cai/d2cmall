package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.FieldContent;
import com.d2c.content.query.FieldContentSearcher;

/**
 * 提供展示块定义的相关数据库操作
 */
public interface FieldContentService {

    /**
     * 以Block对象作为参数，将展示块信息插入view_block表中。并更新Cache
     *
     * @param block
     * @return Block
     */
    FieldContent insert(FieldContent block);

    /**
     * 以Block对象作为参数，修改对应展示块信息。 并更新Cache
     *
     * @param block
     * @return int
     */
    int update(FieldContent block);

    /**
     * 根据id获取展示块信息。
     *
     * @param id
     * @return Block
     */
    FieldContent findOneById(Long id);

    /**
     * 以Block对象作为参数，将展示块信息从view_block表中删除。并更新Cache
     *
     * @param block
     * @return int
     */
    int delete(FieldContent block);

    /**
     * 以searcher对象中包含的过滤条件从view_block表中获取所有符合条件的展示块信息，以PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<FieldContent> findBySearch(FieldContentSearcher searcher, PageModel page);

    /**
     * 按照组名称，页面模块id，采用PageModel分页，以列表形式返回展示块信息。
     *
     * @param groupName 组名称
     * @param pageId    页面模块id
     * @param page      分页
     * @return
     */
    PageResult<FieldContent> findByGroupAndPageId(String groupName, Long pageId, PageModel page);

    int countByGroupAndPage(String group, Long pageid);

}

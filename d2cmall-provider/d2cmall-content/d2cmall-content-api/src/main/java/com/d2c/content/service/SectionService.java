package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.Section;
import com.d2c.content.query.SectionSearcher;

/**
 * 提供板块相关的数据库操作
 */
public interface SectionService {

    /**
     * 根据SectionSearcher对象中的过滤条件获取相应板块信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<Section> findBySearcher(SectionSearcher searcher, PageModel page);

    /**
     * 根据SectionSearcher对象中的过滤条件获取相应板块总数。
     *
     * @param searcher 过滤器
     * @return int
     */
    int countBySearcher(SectionSearcher searcher);

    /**
     * 根据id获取板块信息。
     *
     * @param id
     * @return Section
     */
    Section findById(Long id);

    /**
     * 根据id删除section信息。
     *
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * module删除时，对应删除
     *
     * @param moduleId
     * @return
     */
    int deleteByModuleId(Long moduleId);

    /**
     * 将Section对象插入section表中。
     *
     * @param section
     * @return Section
     */
    Section insert(Section section);

    /**
     * 更新section对象信息。
     *
     * @param section
     * @return int
     */
    int update(Section section);

}

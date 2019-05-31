package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionValueSearcher;

import java.util.List;

/**
 * 提供版块内容的相关数据库操作
 */
public interface SectionValueService {

    /**
     * 根据SectionValueSearcher对象内的过滤信息获取相应信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<SectionValue> findBySearcher(SectionValueSearcher searcher, PageModel page);

    /**
     * 根据SectionValueSearcher对象内的过滤信息获取相应信息总数。
     *
     * @param searcher 过滤器
     * @return int
     */
    int countBySearcher(SectionValueSearcher searcher);

    /**
     * 根据SectionValueSearcher对象内的过滤信息获取相应信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<SectionValueDto> findDtoBySearcher(SectionValueSearcher searcher, PageModel page);

    /**
     * 查询已经删除的
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SectionValue> findDeletedBySearcher(SectionValueSearcher searcher, PageModel page);

    /**
     * 根据section查询
     *
     * @param section
     * @param page
     * @return
     */
    List<SectionValueDto> findBySection(Section section, PageModel page);

    /**
     * 根据id获取板块内容。
     *
     * @param id
     * @return SectionValue
     */
    SectionValue findById(Long id);

    /**
     * 根据id删除相应SectionValue信息。
     *
     * @param id
     * @return int
     */
    int delete(Long id, Long sectionId);

    /**
     * section删除时，对应删除
     *
     * @param sectionId
     * @return
     */
    int deleteBySectionId(Long sectionId);

    /**
     * module删除时，对应删除
     *
     * @param moduleId
     * @return
     */
    int deleteByModuleId(Long moduleId);

    /**
     * 将SectionValue对象插入section_value表中。
     *
     * @param entity
     * @return SectionValue
     */
    SectionValue insert(SectionValue entity);

    /**
     * 修改对应的SectionValue信息。
     *
     * @param sectionValue
     * @return int
     */
    int update(SectionValue sectionValue);

    /**
     * 上下架
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 恢复
     *
     * @return
     */
    int doRecovery(Long id);

}

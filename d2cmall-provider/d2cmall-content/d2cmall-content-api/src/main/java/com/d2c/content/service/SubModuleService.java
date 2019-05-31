package com.d2c.content.service;

import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.Section;
import com.d2c.content.model.SubModule;
import com.d2c.content.search.model.SearcherSection;

import java.util.List;

/**
 * 频道中的页面（sub_module）
 */
public interface SubModuleService {

    /**
     * 根据id更改是否是默认页面，同一级只能有一个是默认页面，并刷新Cache中对应内容。
     *
     * @param id
     * @param is_default
     * @return int
     */
    int updateDefault(Long id, int is_default);

    /**
     * 更新页面信息，并刷新Cache中对应内容。
     *
     * @param subModule
     * @return int
     */
    int update(SubModule subModule);

    /**
     * 将SubModule对象插入sub_module表中，并刷新Cache中对应内容。
     *
     * @param subModule
     * @return SubModule
     */
    SubModule insert(SubModule subModule);

    /**
     * 根据id删除页面，同时删除对应的版面内容，并刷新Cache中对应内容。
     *
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * 更改状态
     *
     * @param moduleId
     * @param status
     * @param username
     * @return
     */
    int updateStatus(Long moduleId, Integer status, String username);

    /**
     * 根据id获取页面信息，后台维护用，不能走缓存，防止有问题
     *
     * @param id
     * @return SubModule
     */
    SubModule findById(Long id);

    /**
     * 刷新版本号
     *
     * @param id
     * @return
     */
    int updateVersion(Long id, Integer version);

    /**
     * 查找分类
     *
     * @return
     */
    List<SubModule> findCategory(String parent);

    /**
     * 修改大页面
     *
     * @param id
     * @param catgegoryId
     * @return
     */
    int updateCategoryId(Long id, Long categoryId);

    /**
     * 发布
     *
     * @param moduleId
     * @return
     */
    int doPublish(Long moduleId);

    /**
     * 发布section
     *
     * @param id
     * @return
     */
    int doPublishSection(Long sectionId);

    /**
     * 查询对应sectionn内容
     *
     * @param section
     * @param DtoPager
     * @param version
     * @return
     */
    List<SearcherSection> findUnFixSection(Section section, PageResult<SectionValueDto> DtoPager, Integer version);

    /**
     * 查询对应sectionn内容
     *
     * @param section
     * @param version
     * @return
     */
    SearcherSection findFixSection(Section section, Integer version);

}

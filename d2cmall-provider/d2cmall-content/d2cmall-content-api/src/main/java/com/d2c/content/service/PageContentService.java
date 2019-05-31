package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.dto.PageContentDto;
import com.d2c.content.model.PageContent;

/**
 * 提供页面展示布局的相关数据库操作
 */
public interface PageContentService {

    /**
     * 根据页面内置id获取页面布局基本信息，再根据页面基本信息获取页面的自定义字段信息，
     * 最后根据页面id以及自定义字段code，获取属于该页面的展示块信息（分页形式）。最后以 Dto对象返回。
     *
     * @param id     页面内置id
     * @param status 状态（1、发布，0、草稿,-1、历史版本）
     * @param page   分页
     * @return Dto
     */
    PageContentDto findOneByModule(Long id, int status, PageModel page);

    /**
     * 以 对象作为参数，将页面展示布局信息插入view_module_page表中。
     *
     * @param homePage
     * @return
     */
    PageContent insert(PageContent homePage);

    /**
     * 复制 内容，并置状态为草稿
     *
     * @param homePage
     * @return
     */
    PageContent doCopyInsert(PageContent homePage);

    /**
     * 根据页面id以及状态，获取页面布局基本信息，再根据页面基本信息获取页面的自定义字段信息，
     * 最后根据页面id以及自定义字段code，获取属于该页面的展示块信息。最后以 Dto对象返回。
     *
     * @param id     页面id
     * @param status 状态（1、发布，0、草稿,-1、历史版本）
     * @return
     */
    PageContentDto findOneByModule(Long id, int status);

    /**
     * 根据页面id，获取页面布局基本信息，再根据页面基本信息获取页面的自定义字段信息，
     * 最后根据页面id以及自定义字段code，获取属于该页面的展示块信息。最后以 Dto对象返回。
     *
     * @param id 页面id
     * @return
     */
    PageContentDto findOneById(Long id);

    PageContentDto findOneById(Long id, PageModel pager);

    /**
     * 将对象发布为线上页面，根据当前发布页面的pageDefId，删除原来的历史记录，并将现在的 变为历史记录。
     *
     * @param modulePage
     * @return int
     */
    int doPublish(PageContent modulePage);

    /**
     * 以 对象作为参数，修改页面展示布局信息。
     *
     * @param homePage
     * @return int
     */
    int update(PageContent homePage);

}

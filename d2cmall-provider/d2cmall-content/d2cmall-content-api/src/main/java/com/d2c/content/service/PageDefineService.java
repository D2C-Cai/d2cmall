package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.PageDefineDto;
import com.d2c.content.model.PageDefine;
import com.d2c.content.model.PageDefine.MODULE;
import com.d2c.content.model.PageDefine.TERMINAL;

/**
 * 提供模块及终端的相关数据库操作
 */
public interface PageDefineService {

    /**
     * 根据pageid获取PageDefine以及其下的FieldDef信息，封装成PageDefineDto对象返回。
     *
     * @param pageId 页面定义id
     * @return PageDefineDto
     */
    PageDefineDto findAllById(Long pageId);

    /**
     * 根据PageDefine包含的过滤条件获取相应的模块定义，采用分页方式，以PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<PageDefineDto> findBySearch(PageDefine searcher, PageModel page);

    /**
     * 根据根据pageid获取PageDefine信息。
     *
     * @param id
     * @return
     */
    PageDefine findById(Long id);

    /**
     * 复制PageDefine信息。
     *
     * @param id
     * @return
     */
    int doCopy(Long id);

    /**
     * 保存PageDefine对象信息，如果PageDefine对象存在id，那么将其FieldDef信息也保存。
     *
     * @param pageDefine
     * @return PageDefine
     */
    PageDefine insert(PageDefine pageDefine);

    /**
     * 更新PageDefine信息
     *
     * @param pageDefine
     * @return int
     */
    int update(PageDefine pageDefine);

    /**
     * 根据参数获取PageDefineDto类型信息。
     *
     * @param homepage 页面类型
     * @param terminal 终端
     * @param version  版本
     * @return
     */
    PageDefineDto findPageDefine(MODULE homepage, TERMINAL terminal, Integer version);

}

package com.d2c.logger.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.Template;
import com.d2c.logger.query.TemplateSearcher;

import java.util.List;

/**
 * 模板（template）
 */
public interface TemplateService {

    /**
     * 根据id获取模板信息
     *
     * @param id 主键id
     * @return
     */
    Template findById(Long id);

    /**
     * 根据id数组返回模板列表
     *
     * @param ids id数组
     * @return
     */
    List<Template> findByIds(Long[] ids);

    /**
     * 根据TemplateSearcher内的过滤条件，获取模板信息 采用分页形式，以PageResult对象返回
     *
     * @param templateSearcher 过滤器
     * @param page             分页
     * @return
     */
    PageResult<Template> findBySearch(TemplateSearcher templateSearcher, PageModel page);

    /**
     * 根据TemplateSearcher内的过滤条件，获取模板信息 采用分页形式，以PageResult对象返回，提供给doHelp使用
     *
     * @param templateSearcher 过滤器
     * @param page             分页
     * @return
     */
    List<HelpDTO> findBySearchForHelp(TemplateSearcher templateSearcher, PageModel page);

    /**
     * 保存模板信息
     *
     * @param template
     * @return
     * @throws Exception
     */
    Template insert(Template template) throws Exception;

    /**
     * 更新模板
     *
     * @param template
     * @return
     * @throws Exception
     */
    int update(Template template) throws Exception;

    /**
     * 根据id删除模板
     *
     * @param id 主键id
     * @return
     * @throws Exception
     */
    int delete(Long id) throws Exception;

}

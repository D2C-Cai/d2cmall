package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.SubModule;
import com.d2c.content.query.PageContentSearcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("subModuleService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public interface SubModuleQueryService {

    /**
     * 根据id获取页面信息，优先从Cache中读取。
     *
     * @param id
     * @return SubModule
     */
    SubModule findById(Long id);

    /**
     * 根据parent信息获取子页面信息，以列表形式返回。
     *
     * @param parent
     * @return List<E>
     */
    List<SubModule> findByParent(String parent);

    /**
     * 查找分类下的页面
     *
     * @param parent
     * @param category
     * @return
     */
    List<SubModule> findByParentAndCategory(String parent, Long categoryId);

    /**
     * 根据SubModuleSearcher对象中的过滤条件获取页面信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<SubModule> findBySearcher(PageContentSearcher searcher, PageModel page);

    /**
     * 根据SubModuleSearcher对象中的过滤条件获取页面总数。
     *
     * @param searcher
     * @return int
     */
    int countBySearcher(PageContentSearcher searcher);

    /**
     * 清除缓存
     *
     * @param parent
     */
    void clearCacheByParent(String parent);

    /**
     * 清理分类缓存
     *
     * @param categoryId
     */
    void clearCacheByCategory(Long categoryId);

    /**
     * 查出所有分类
     *
     * @return
     */
    List<SubModule> findAllCategory(String parent);

}

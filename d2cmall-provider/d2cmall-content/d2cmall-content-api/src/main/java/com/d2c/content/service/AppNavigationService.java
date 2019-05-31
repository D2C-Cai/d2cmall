package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.AppNavigation;
import com.d2c.content.query.AppNavigationSearcher;

import java.util.List;

/**
 * app底部导航栏
 *
 * @author Administrator
 */
public interface AppNavigationService {

    /**
     * 新增
     *
     * @param appNavigation
     * @return
     */
    AppNavigation insert(AppNavigation appNavigation);

    /**
     * 更新
     *
     * @param appNavigation
     * @return
     */
    int update(AppNavigation appNavigation);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    AppNavigation findById(Long id);

    /**
     * 根据条件查找
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AppNavigation> findBySearcher(AppNavigationSearcher searcher, PageModel page);

    /**
     * 查找所有上架的
     *
     * @return
     */
    List<AppNavigation> findAllEnable();

    /**
     * 更新背景颜色
     *
     * @param id
     * @param backColor
     * @return
     */
    int updateBackColor(Long id, String backColor, String lastModifyMan);

}

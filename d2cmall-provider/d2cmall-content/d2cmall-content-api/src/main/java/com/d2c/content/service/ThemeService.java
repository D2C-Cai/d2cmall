package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ThemeDto;
import com.d2c.content.model.Theme;
import com.d2c.content.query.ThemeSearcher;

/**
 * 专题
 *
 * @author Lain
 */
public interface ThemeService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Theme findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param page
     * @return
     */
    PageResult<ThemeDto> findDtoBySearcher(ThemeSearcher searcher, PageModel page);

    /**
     * 新增
     *
     * @param theme
     * @return
     */
    Theme insert(Theme theme);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 更新
     *
     * @param theme
     * @return
     */
    int update(Theme theme);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 推荐
     *
     * @param id
     * @param recomment
     * @return
     */
    int updateRecommend(Long id, Integer recommend);

    /**
     * 根据tagId 查找
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<Theme> findByTagId(Long tagId, PageModel page);

}

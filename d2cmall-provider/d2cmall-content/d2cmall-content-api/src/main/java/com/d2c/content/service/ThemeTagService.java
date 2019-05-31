package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ThemeTagDto;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;

import java.util.List;

/**
 * 专题分类
 *
 * @author Lain
 */
public interface ThemeTagService {

    /**
     * 查询
     *
     * @param id
     * @return
     */
    ThemeTag findById(Long id);

    /**
     * 查询固定标签
     *
     * @return
     */
    ThemeTag findFixedOne();

    /**
     * 全部专题标签
     *
     * @return
     */
    List<ThemeTag> findAll(String type);

    /**
     * 查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ThemeTag> findBySearcher(ThemeTagSearcher searcher, PageModel page);

    /**
     * 各标签下的专题数量
     *
     * @return
     */
    List<ThemeTagDto> countGroupByTag(String type, ThemeTagSearcher searcher);

    /**
     * 新增
     *
     * @param themeTag
     * @return
     */
    ThemeTag insert(ThemeTag themeTag);

    /**
     * 更新
     *
     * @param themeTag
     * @return
     */
    int update(ThemeTag themeTag);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

}

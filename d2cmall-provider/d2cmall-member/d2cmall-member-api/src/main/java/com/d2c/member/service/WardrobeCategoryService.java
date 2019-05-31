package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.WardrobeCategory;
import com.d2c.member.query.WardrobeCategorySearcher;

import java.util.List;

public interface WardrobeCategoryService {

    /**
     * 新增
     *
     * @param wardrobeCategory
     * @return
     */
    WardrobeCategory insert(WardrobeCategory wardrobeCategory);

    /**
     * 更新
     *
     * @param wardrobeCategory
     * @return
     */
    int update(WardrobeCategory wardrobeCategory);

    /**
     * 根据searcher查询分页数据
     *
     * @param query
     * @param page
     * @return
     */
    PageResult<WardrobeCategory> findBySearcher(WardrobeCategorySearcher query, PageModel page);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    WardrobeCategory findById(Long id);

    /**
     * 根据一级分类查询，前端用
     *
     * @param topName
     * @param status
     * @return
     */
    List<WardrobeCategory> findByTop(String topName, Integer status);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 根据分类名称查询
     *
     * @param name
     * @return
     */
    WardrobeCategory findByName(String name);

    /**
     * 后台删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

}

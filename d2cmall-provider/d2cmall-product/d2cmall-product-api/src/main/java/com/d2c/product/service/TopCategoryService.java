package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.TopCategory;
import com.d2c.product.query.TopCategorySearcher;

import java.util.List;

/**
 * 实体类 - 一级分类(product_top_category)
 */
public interface TopCategoryService {

    /**
     * 根据TopCategorySearcher过滤器内的过滤条件，获取一级分类信息 采用分页方式，以PageResult对象返回数据。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<TopCategory> findBySearch(TopCategorySearcher searcher, PageModel page);

    /**
     * 根据状态获取一级分类信息，以列表形式返回
     *
     * @param status 状态
     * @return
     */
    List<TopCategory> findAll(Integer status);

    /**
     * 根据id获取TopCategory实体
     *
     * @param id 主键id
     * @return
     */
    TopCategory findById(Long id);

    /**
     * 根据code查询
     *
     * @param code
     * @return
     */
    TopCategory findByCode(String code);

    /**
     * 保存一级分类信息
     *
     * @param topCategory
     * @return
     */
    TopCategory insert(TopCategory topCategory);

    /**
     * 更新一级分类信息
     *
     * @param topCategory
     * @return
     */
    int update(TopCategory topCategory);

    /**
     * 根据id，更新状态
     *
     * @param id     主键id
     * @param status 状态
     * @return
     */
    int updateStatus(Long id, Integer status);

}

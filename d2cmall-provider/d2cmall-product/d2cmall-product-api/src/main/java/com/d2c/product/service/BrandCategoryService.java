package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.query.BrandCategorySearcher;

import java.util.List;

/**
 * 品牌分类（brandCategory）
 */
public interface BrandCategoryService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BrandCategory findById(Long id);

    /**
     * 根据设计领域查询
     *
     * @param designareatype
     * @return
     */
    List<BrandCategory> findByType(String designareatype);

    /**
     * 根据名称和类型查询
     *
     * @param name
     * @param type
     * @return
     */
    BrandCategory findByNameAndType(String name, String type);

    /**
     * 根据code和类型查询
     *
     * @param parseLong
     * @param string
     * @return
     */
    BrandCategory findByCodeAndType(String code, String type);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandCategory> findBySearch(BrandCategorySearcher searcher, PageModel page);

    /**
     * 保存数据
     *
     * @param brandCategory
     * @return
     * @throws Exception
     */
    BrandCategory insert(BrandCategory brandCategory) throws Exception;

    /**
     * 更新数据
     *
     * @param brandCategory
     * @return
     * @throws Exception
     */
    int update(BrandCategory brandCategory) throws Exception;

    /**
     * 根据id更新排序
     *
     * @param id
     * @param orderList
     * @return
     * @throws Exception
     */
    int updateSort(Long id, Integer orderList) throws Exception;

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    int updateStatus(Long id, Integer status) throws Exception;

}

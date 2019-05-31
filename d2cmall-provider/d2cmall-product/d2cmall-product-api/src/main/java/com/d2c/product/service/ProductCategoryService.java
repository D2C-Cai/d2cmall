package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.ProductCategoryDto;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.query.ProductCategorySearcher;

import java.util.List;

/**
 * 商品- 二级分类（product_category）
 */
public interface ProductCategoryService {

    /**
     * 根据ProductCategorySearcher内的过滤条件，获取二级分类信息 采用分页方式，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductCategoryDto> findBySearch(ProductCategorySearcher searcher, PageModel page);

    /**
     * 对所有需要排序的二级分类进行排序
     *
     * @param all  需要排序的二级分类集合
     * @param p    父节点
     * @param temp 临时队列
     * @return
     */
    List<ProductCategoryDto> processSequence(List<ProductCategoryDto> all, Long p, List<ProductCategoryDto> temp);

    /**
     * 根据id获取二级分类信息
     *
     * @param id 主键id
     * @return
     */
    ProductCategory findById(Long id);

    /**
     * 保存二级分类信息
     *
     * @param productCategory
     * @return
     */
    ProductCategory insert(ProductCategory productCategory);

    /**
     * 更新二级分类信息
     *
     * @param productCategory
     * @return
     */
    int update(ProductCategory productCategory);

    /**
     * 根据id更新二级分类状态
     *
     * @param id     主键id
     * @param status 状态（停用 0/启用 1）
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 根据状态获取所有二级分类信息
     *
     * @param status 状态（停用 0/启用 1）
     * @return
     */
    List<ProductCategory> findAll(Integer status);

    /**
     * 根据树路径获取二级分类信息
     *
     * @param path 树路径
     * @return
     */
    List<ProductCategory> findInSet(String path);

    /**
     * 通过代号得到商品类别
     *
     * @param code
     * @return
     */
    ProductCategory findByCode(String code);

    /**
     * 通过底部节点查找品类
     *
     * @param categoryId
     * @return
     */
    List<ProductCategory> findByBottomId(Long categoryId);

    /**
     * 根据topId获取品类
     *
     * @param topId
     * @return
     */
    List<ProductCategory> findByTopId(Long topId);

}

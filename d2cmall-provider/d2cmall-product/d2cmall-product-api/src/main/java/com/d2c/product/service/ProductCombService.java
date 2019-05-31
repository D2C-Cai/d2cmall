package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.model.ProductComb;
import com.d2c.product.query.ProductCombSearcher;

/**
 * 组合商品（productcomb）
 */
public interface ProductCombService {

    /**
     * 根据ProductCombSearcher内的过滤条件，获取对应组合商品信息 采用分页方式，以PageResult对方返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductCombDto> findBySearcher(ProductCombSearcher searcher, PageModel page);

    /**
     * 根据主键id获取组合产品信息
     *
     * @param id 产品组主键id
     * @return
     */
    ProductComb findById(Long id);

    /**
     * 根据主键id获取组合产品信息
     *
     * @param id 产品组主键id
     * @return
     */
    ProductCombDto findDtoById(Long id);

    /**
     * 根据主键id，删除组合产品
     *
     * @param id 产品组主键id
     * @return
     */
    int delete(Long id);

    /**
     * 更新对应组合产品的状态
     *
     * @param id   产品组主键id
     * @param mark 商品状态标志（0:下架，1：上架,-1 删除）
     * @return
     */
    int updateMark(Long id, int mark);

    /**
     * 保存组合产品
     *
     * @param productComb
     * @return
     */
    ProductComb insert(ProductComb productComb);

    /**
     * 更新组合产品
     *
     * @param productComb
     * @return
     */
    int update(ProductComb productComb);

    /**
     * 清除缓存
     *
     * @param sourceId
     */
    void doClearBySourceId(Long sourceId);

}

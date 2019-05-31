package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductAttribute;
import com.d2c.product.query.ProductAttributeSearcher;

import java.util.List;

/**
 * 商品属性定义（product_attribute）
 */
public interface ProductAttributeService {

    /**
     * 根据ProductAttributeSearcher内过滤条件，获取相关的商品属性定义信息 采用分页方式，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductAttribute> findBySearch(ProductAttributeSearcher searcher, PageModel page);

    /**
     * 根据主键id获取商品属性定义
     *
     * @param id 主键id
     * @return
     */
    ProductAttribute findById(Long id);

    /**
     * 保存商品属性定义
     *
     * @param productAttribute
     * @return
     */
    ProductAttribute insert(ProductAttribute productAttribute);

    /**
     * 更新商品属性定义
     *
     * @param productAttribute
     * @return
     */
    int update(ProductAttribute productAttribute);

    /**
     * 根据属性组id获取该属性组下的所有商品属性
     *
     * @param attributeGroupId 属性组ID
     * @return
     */
    List<ProductAttribute> findByGroupId(Long attributeGroupId);

}

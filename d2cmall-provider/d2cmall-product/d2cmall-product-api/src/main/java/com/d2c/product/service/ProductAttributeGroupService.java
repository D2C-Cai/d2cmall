package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductAttributeGroup;
import com.d2c.product.query.ProductAttributeGroupSearcher;

/**
 * 商品属性组（product_attribute_group）
 */
public interface ProductAttributeGroupService {

    /**
     * 根据ProductAttributeGroupSearcher内过滤条件，获取商品属性组信息 采用分页方式，以PageResult对方返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductAttributeGroup> findBySearch(ProductAttributeGroupSearcher searcher, PageModel page);

    /**
     * 根据属性组id获取对应属性组信息
     *
     * @param groupId 属性组id
     * @return
     */
    ProductAttributeGroup findById(Long groupId);

    /**
     * 保存商品属性组信息
     *
     * @param productAttributeGroup
     * @return
     */
    ProductAttributeGroup insert(ProductAttributeGroup productAttributeGroup);

    /**
     * 更新商品属性组信息
     *
     * @param productAttributeGroup
     * @return
     */
    int update(ProductAttributeGroup productAttributeGroup);

}

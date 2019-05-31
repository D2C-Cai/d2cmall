package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductPartnerRelation;

public interface ProductPartnerRelationService {

    /**
     * 插入一条记录
     *
     * @param storeId
     * @param productId
     * @return
     */
    int insert(Long storeId, Long productId);

    /**
     * 删除一条记录
     *
     * @param storeId
     * @param productId
     * @return
     */
    int deleteOne(Long storeId, Long productId);

    /**
     * 查询一条记录
     *
     * @param storeId
     * @param productId
     * @return
     */
    ProductPartnerRelation findOne(Long storeId, Long productId);

    /**
     * 查询已绑定的商品
     *
     * @param storeId
     * @param page
     * @return
     */
    PageResult<Product> findProductByStoreId(Long storeId, PageModel page);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ProductPartnerRelation findById(Long id);

    /**
     * 更新排序
     *
     * @param storeId
     * @param productId
     * @param sort
     * @return
     */
    int updateSort(Long storeId, Long productId, Integer sort);

}

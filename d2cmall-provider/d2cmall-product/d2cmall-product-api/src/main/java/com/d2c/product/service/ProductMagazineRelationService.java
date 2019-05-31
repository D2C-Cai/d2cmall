package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductMagazineRelation;

public interface ProductMagazineRelationService {

    /**
     * 插入一条记录
     *
     * @param pageId
     * @param productId
     * @return
     */
    int insert(Long pageId, Long productId);

    /**
     * 删除一条记录
     *
     * @param pageId
     * @param productId
     * @return
     */
    int deleteOne(Long pageId, Long productId);

    /**
     * 查询已绑定的商品
     *
     * @param pageId
     * @param page
     * @return
     */
    PageResult<Product> findProductByPageId(Long pageId, PageModel page);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ProductMagazineRelation findById(Long id);

    /**
     * 更新排序
     *
     * @param pageId
     * @param productId
     * @param sort
     * @return
     */
    int updateSort(Long pageId, Long productId, Integer sort);

}

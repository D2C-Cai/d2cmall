package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.query.ProductShareRelationSearcher;

import java.util.List;

public interface ProductShareRelationService {

    /**
     * 插入关系
     *
     * @param productShareRelation
     * @return
     */
    int doReplace(ProductShareRelation productShareRelation);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ProductShareRelation> findBySearcher(ProductShareRelationSearcher searcher, PageModel page);

    /**
     * 根据shareId查询
     *
     * @param shareId
     * @param pageSize
     * @return
     */
    List<ProductShareRelation> findByShareId(Long shareId, Integer pageSize);

    /**
     * 删除关联商品
     *
     * @param shareId
     * @param productId
     * @return
     */
    int deleteRelation(Long shareId, Long productId);

    /**
     * 查询绑定该买家秀的商品ID
     *
     * @param shareId
     * @return
     */
    List<Long> findProductIdByShareId(Long shareId);

}

package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.ProductTagRelationDto;
import com.d2c.product.model.ProductTagRelation;
import com.d2c.product.query.ProductSearcher;

public interface ProductTagRelationService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ProductTagRelation findById(Long id);

    /**
     * 根据商品id以及标签id查找是否存在对应的关系
     *
     * @param productId 商品id
     * @param tagId     标签id
     * @return
     */
    ProductTagRelation findByTagIdAndProductId(Long productId, Long tagId);

    /**
     * 保存商品的标签
     *
     * @param productId 商品id
     * @param tagIds    标签id
     * @return
     */
    int insert(Long productId, Long tagId, String operator);

    /**
     * 根据id删除
     *
     * @param relationId
     * @param operator
     * @return
     */
    int delete(Long relationId, String operator);

    /**
     * 根据商品id以及标签id删除他们的关联关系
     *
     * @param tagId     标签id
     * @param productId 商品id
     * @return
     */
    int deleteByTagIdAndProductId(Long tagId, Long productId, String operator);

    /**
     * 更新商品的标签排序
     *
     * @param tagId     标签id
     * @param productId 商品id
     * @param sort      排序序号
     * @return
     */
    int updateSort(Long id, int sort);

    /**
     * 标签关联的商品列表
     *
     * @param page
     * @param productSearcher
     * @param tagId
     * @return
     */
    PageResult<ProductTagRelationDto> findProductsByTagId(PageModel page, ProductSearcher productSearcher, Long tagId);

    /**
     * 商品关联的标签列表
     *
     * @param page
     * @param productId
     * @return
     */
    PageResult<ProductTagRelationDto> findTagsByProductId(PageModel page, Long productId);

    /**
     * 商品关联的标签id
     *
     * @param productId
     * @return
     */
    Long[] findTagIdByProductId(Long productId);

}

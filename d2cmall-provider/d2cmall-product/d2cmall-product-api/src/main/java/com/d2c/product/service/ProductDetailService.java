package com.d2c.product.service;

import com.d2c.product.model.ProductDetail;

/**
 * 产品详细，准静态数据（product_detail）
 */
public interface ProductDetailService {

    /**
     * 根据产品id获取产品详情
     *
     * @param id 产品id
     * @return
     */
    ProductDetail findByProductId(Long id);

    /**
     * 更新产品详细
     *
     * @param productDetail
     * @return
     */
    int update(ProductDetail productDetail);

    /**
     * 保存产品详细
     *
     * @param productDetail
     * @return
     */
    ProductDetail insert(ProductDetail productDetail);

    /**
     * 根据产品id更新参数组id
     *
     * @param productId        产品id
     * @param attributeGroupId 参数组ID
     * @return
     */
    int updateAttribute(Long productId, Long attributeGroupId);

    /**
     * 根据商品ID更新
     *
     * @param productDetail
     * @return
     */
    int updateByProductId(ProductDetail productDetail);

    /**
     * 删除尺码表
     *
     * @param id
     * @return
     */
    int deleteSizeJson(Long id);

}

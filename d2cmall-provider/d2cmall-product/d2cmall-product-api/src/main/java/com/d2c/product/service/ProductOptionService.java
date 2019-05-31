package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductOption;
import com.d2c.product.model.ProductSkuOption;
import com.d2c.product.query.ProductOptionSearcher;

import java.util.List;

public interface ProductOptionService {

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    ProductOption findById(Long id);

    /**
     * 商品列表
     *
     * @param productSearcher
     * @param page
     * @return
     */
    PageResult<ProductOption> findBySearch(ProductOptionSearcher productSearcher, PageModel page);

    /**
     * 商品数量
     *
     * @param productSearcher
     * @return
     */
    int countBySearch(ProductOptionSearcher productSearcher);

    /**
     * 新增
     *
     * @param productOption
     * @param skus
     * @return
     */
    ProductOption insert(ProductOption productOption, List<ProductSkuOption> skus);

    /**
     * 修改
     *
     * @param productOption
     * @return
     */
    int update(ProductOption productOption, List<ProductSkuOption> skus);

    /**
     * 视频地址
     *
     * @param productId
     * @param video
     * @return
     */
    int updateVideoById(Long productId, String video);

    /**
     * 提交审核
     *
     * @param id
     * @return
     */
    int doSubmit(Long id);

    /**
     * 拒绝
     *
     * @param id
     * @return
     */
    int doRefuse(Long id, String refuseReason);

    /**
     * 通过
     *
     * @param id
     * @return
     */
    int doMarkSuccess(Long id);

    /**
     * 修改价格
     *
     * @param productId
     * @return
     */
    int updatePriceBySku(Long productId);

    /**
     * 修改颜色尺码
     *
     * @param skuSet
     * @param productId
     * @return
     */
    int updateSalesPropertyBySku(List<ProductSkuOption> skuSet, Long productId);

}

package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.ProductSkuStockDto;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.query.ProductSkuStockSearcher;

import java.util.List;

public interface ProductSkuStockSummaryService {

    /**
     * 更新顺丰库存
     *
     * @return
     */
    int sumSfStock();

    /**
     * 更新门店库存
     *
     * @return
     */
    int sumStStock();

    /**
     * 新增summary
     *
     * @param productSkuStockSummary
     * @return
     */
    ProductSkuStockSummary insert(ProductSkuStockSummary productSkuStockSummary);

    /**
     * 根据skuId查询
     *
     * @param skuId
     * @return
     */
    ProductSkuStockSummary findBySkuId(Long skuId);

    /**
     * 根据商品ID查询
     *
     * @param productId
     * @return
     */
    List<ProductSkuStockSummary> findByProductId(Long productId);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ProductSkuStockDto> findBySearch(ProductSkuStockSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(ProductSkuStockSearcher searcher);

    /**
     * 根据skuId更新
     *
     * @param barCode
     * @param skuId
     * @return
     */
    int updateSkuBySkuId(String barCode, Long skuId, String sp1, String sp2);

    int delete(Long id);

}

package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.dto.ProductSkuStockDto;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.query.ProductSkuStockSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuStockSummaryMapper extends SuperMapper<ProductSkuStockSummary> {

    int initSfStock();

    int sumSfStock();

    int initStStock();

    int sumStStock();

    ProductSkuStockSummary findBySkuId(Long skuId);

    List<ProductSkuStockSummary> findByProductId(Long productId);

    List<ProductSkuStockDto> findBySearch(@Param("searcher") ProductSkuStockSearcher productSearcher,
                                          @Param("pager") PageModel page);

    int countBySearch(@Param("searcher") ProductSkuStockSearcher productSearcher);

    int updateSkuBySkuId(@Param("barCode") String barCode, @Param("skuId") Long skuId, @Param("sp1") String sp1,
                         @Param("sp2") String sp2);

    int delete(Long id);

}

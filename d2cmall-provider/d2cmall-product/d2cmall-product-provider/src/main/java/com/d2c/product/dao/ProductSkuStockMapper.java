package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuStockMapper extends SuperMapper<ProductSkuStock> {

    int deleteAll();

    int delete(@Param("storeCode") String storeCode, @Param("barCode") String barCode);

    int update(@Param("storeCode") String storeCode, @Param("barCode") String barCode, @Param("stock") int stock);

    ProductSkuStock findOne(@Param("storeCode") String storeCode, @Param("barCode") String barCode);

    List<ProductSkuStock> findBySkuAndStore(@Param("productSkuSn") String sku, @Param("isPrimary") boolean isPrimary);

    int updateOccupyStock(@Param("storeCode") String storeCode, @Param("barCode") String barCode,
                          @Param("stock") int stock);

    int doCleanOccupy();

    List<ProductSkuStock> findStoreBySku(@Param("productSkuSn") String sku);

    List<ProductSkuStock> findByStore(@Param("storeCode") String storeCode, @Param("pager") PageModel page);

    int countByStore(@Param("storeCode") String storeCode);

}

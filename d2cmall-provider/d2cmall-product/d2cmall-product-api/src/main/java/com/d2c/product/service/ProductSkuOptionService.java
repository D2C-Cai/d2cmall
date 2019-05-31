package com.d2c.product.service;

import com.d2c.product.model.ProductSkuOption;

import java.util.List;

public interface ProductSkuOptionService {

    ProductSkuOption insert(ProductSkuOption productSkuOption);

    int update(ProductSkuOption productSkuOption);

    List<ProductSkuOption> findByProductId(Long productId);

    int updateMarkByProductId(Long productId, Integer mark);

    int delete(Long id);

    ProductSkuOption findById(Long id);

}

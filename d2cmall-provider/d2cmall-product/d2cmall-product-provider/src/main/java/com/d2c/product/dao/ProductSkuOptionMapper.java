package com.d2c.product.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductSkuOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuOptionMapper extends SuperMapper<ProductSkuOption> {

    List<ProductSkuOption> findByProductId(Long productId);

    int updateMarkByProductId(@Param("productId") Long productId, @Param("mark") Integer mark);

    int delete(Long id);

}

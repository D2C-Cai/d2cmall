package com.d2c.product.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductDetail;
import org.apache.ibatis.annotations.Param;

public interface ProductDetailMapper extends SuperMapper<ProductDetail> {

    ProductDetail findByProductId(Long productId);

    int updateAttribute(@Param("productId") Long productId, @Param("attributeGroupId") Long attributeGroupId);

    int updateSizeJson(@Param("productId") Long productId, @Param("sizeJson") String sizeJson);

}

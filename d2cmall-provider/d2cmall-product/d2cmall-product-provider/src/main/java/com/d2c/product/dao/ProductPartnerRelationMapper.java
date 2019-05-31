package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductPartnerRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductPartnerRelationMapper extends SuperMapper<ProductPartnerRelation> {

    int doReplaceInto(@Param("storeId") Long storeId, @Param("productId") Long productId);

    int deleteOne(@Param("storeId") Long storeId, @Param("productId") Long productId);

    ProductPartnerRelation findOne(@Param("storeId") Long storeId, @Param("productId") Long productId);

    List<Product> findProductByStoreId(@Param("storeId") Long storeId, @Param("pager") PageModel pager);

    int countProductByStoreId(@Param("storeId") Long storeId);

    int deleteProductByStoreId(@Param("storeId") Long storeId);

    int updateSort(@Param("storeId") Long storeId, @Param("productId") Long productId, @Param("sort") Integer sort);

}

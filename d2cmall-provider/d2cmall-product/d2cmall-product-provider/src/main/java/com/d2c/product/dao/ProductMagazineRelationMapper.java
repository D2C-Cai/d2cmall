package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductMagazineRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMagazineRelationMapper extends SuperMapper<ProductMagazineRelation> {

    int doReplaceInto(@Param("pageId") Long pageId, @Param("productId") Long productId);

    int deleteOne(@Param("pageId") Long pageId, @Param("productId") Long productId);

    List<Product> findProductByPageId(@Param("pageId") Long pageId, @Param("pager") PageModel pager);

    int countProductByPageId(@Param("pageId") Long pageId);

    int deleteProductByPageId(@Param("pageId") Long pageId);

    int updateSort(@Param("pageId") Long pageId, @Param("productId") Long productId, @Param("sort") Integer sort);

}

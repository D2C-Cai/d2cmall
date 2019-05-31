package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductAttribute;
import com.d2c.product.query.ProductAttributeSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductAttributeMapper extends SuperMapper<ProductAttribute> {

    List<ProductAttribute> findByGroupId(Long attributeGroupId);

    List<ProductAttribute> findBySearch(@Param("searcher") ProductAttributeSearcher searcher,
                                        @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ProductAttributeSearcher searcher);

}
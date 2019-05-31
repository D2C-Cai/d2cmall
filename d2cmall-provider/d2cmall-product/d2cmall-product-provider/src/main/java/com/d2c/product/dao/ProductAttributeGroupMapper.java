package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductAttributeGroup;
import com.d2c.product.query.ProductAttributeGroupSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductAttributeGroupMapper extends SuperMapper<ProductAttributeGroup> {

    List<ProductAttributeGroup> findBySearch(@Param("searcher") ProductAttributeGroupSearcher searcher,
                                             @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ProductAttributeGroupSearcher searcher);

}
package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.query.ProductShareRelationSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductShareRelationMapper extends SuperMapper<ProductShareRelation> {

    List<ProductShareRelation> findByShareId(@Param("shareId") Long shareId, @Param("pageSize") Integer pageSize);

    List<ProductShareRelation> findBySearcher(@Param("searcher") ProductShareRelationSearcher searcher,
                                              @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") ProductShareRelationSearcher searcher);

    int doReplace(@Param("productShareRelation") ProductShareRelation productShareRelation);

    int deleteRelation(@Param("shareId") Long shareId, @Param("productId") Long productId);

    List<Long> findProductIdByShareId(Long shareId);

}

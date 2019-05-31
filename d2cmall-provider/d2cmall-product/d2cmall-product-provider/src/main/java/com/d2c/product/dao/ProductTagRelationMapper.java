package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductTagRelation;
import com.d2c.product.query.ProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTagRelationMapper extends SuperMapper<ProductTagRelation> {

    List<ProductTagRelation> findByTagId(Long tagId);

    List<ProductTagRelation> findByProductId(@Param("productId") Long productId, @Param("page") PageModel page);

    int countByProductId(@Param("productId") Long productId);

    ProductTagRelation findByTagIdAndProductId(@Param("productId") Long productId, @Param("tagId") Long tagId);

    Long[] findTagIdByProductId(@Param("productId") Long productId);

    List<ProductTagRelation> findBySearcher(@Param("tagId") Long tagId,
                                            @Param("searcher") ProductSearcher productSearcher, @Param("pager") PageModel page);

    int countBySearcher(@Param("tagId") Long tagId, @Param("searcher") ProductSearcher productSearcher);

    int updateSort(@Param("id") Long id, @Param("sort") int sort);

    int deleteByTagId(Long tagId);

    int deleteByProductId(Long productId);

    int deleteByTagIdAndProductId(@Param("tagId") Long tagId, @Param("productId") Long productId);

}

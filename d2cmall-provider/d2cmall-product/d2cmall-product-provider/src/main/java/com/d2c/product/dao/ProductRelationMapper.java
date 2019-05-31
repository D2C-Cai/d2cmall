package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductRelation;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.RelationSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductRelationMapper extends SuperMapper<ProductRelation> {

    int insertBatch(List<ProductRelation> relations);

    int insertOne(ProductRelation relations);

    int deleteByTypeAndSourceId(@Param("type") String type, @Param("sourceId") Long sourceId);

    int deleteByTypeAndRelationId(@Param("type") String type, @Param("relationId") Long relationId);

    int deleteOne(@Param("type") String type, @Param("relationId") Long relationId, @Param("sourceId") Long sourceId);

    List<ProductRelation> findRelations(@Param("searcher") RelationSearcher searcher, @Param("pager") PageModel pager);

    int countRelations(@Param("searcher") RelationSearcher searcher);

    /**
     * 根据type,source_id,relation_id 更新sort
     */
    int updateSort(@Param("type") RelationType type, @Param("sourceId") Long sourceId,
                   @Param("relationId") Long relationId, @Param("sort") int sort);

    List<Product> findRelationProducts(@Param("sourceId") Long sourceId, @Param("mark") Integer mark);

    List<Long> findSourceIdsByRelationId(@Param("relationId") Long relationId, @Param("type") String type);

    List<Long> findBySourceId(@Param("sourceId") Long sourceId, @Param("type") String type);

}

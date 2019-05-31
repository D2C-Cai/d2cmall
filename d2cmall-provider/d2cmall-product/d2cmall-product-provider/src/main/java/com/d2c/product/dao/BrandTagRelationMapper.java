package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BrandTagRelation;
import com.d2c.product.query.BrandTagRelationSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandTagRelationMapper extends SuperMapper<BrandTagRelation> {

    List<BrandTagRelation> findByTagRelationSearch(@Param("searcher") BrandTagRelationSearcher searcher,
                                                   @Param("pager") PageModel page);

    int countByTagRelationSearch(@Param("searcher") BrandTagRelationSearcher searcher);

    BrandTagRelation findByTagIdAndDesignerId(@Param("tagId") Long tagId, @Param("designerId") Long designerId);

    int deleteByTagIdAndDesignerId(@Param("tagId") Long tagId, @Param("designerId") Long designerId);

    int deleteByTagId(Long tagId);

    int deleteByDesignerId(Long designerId);

    int updateSort(@Param("designerId") Long designerId, @Param("tagId") Long tagId, @Param("sort") Integer sort);

}

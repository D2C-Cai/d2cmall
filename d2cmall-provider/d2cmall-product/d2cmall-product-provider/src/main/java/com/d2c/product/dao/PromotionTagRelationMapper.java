package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.PromotionTagRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PromotionTagRelationMapper extends SuperMapper<PromotionTagRelation> {

    int deleteByPromotionId(Long promotionId);

    int deleteByPromotionIdAndTagId(@Param("promotionId") Long promotionId, @Param("tagId") Long tagId);

    List<PromotionTagRelation> findByPromotionId(Long promotionId);

    int updateSort(@Param("id") Long id, @Param("sort") int sort);

    int countByTagId(Long tagId);

    List<PromotionTagRelation> findByTagId(@Param("tagId") Long tagId, @Param("pager") PageModel page);

}

package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefRelation;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponDefRelationMapper extends SuperMapper<CouponDefRelation> {

    CouponDefRelation findByCidAndTid(@Param("targetId") Long targetId, @Param("couponDefId") Long couponDefId,
                                      @Param("type") String type);

    List<CouponDefRelation> findByCidAndTids(@Param("targetIds") List<Long> targetIds,
                                             @Param("couponDefId") Long couponDefId, @Param("type") String type);

    int deleteByCidAndTid(@Param("targetId") Long targetId, @Param("couponDefId") Long couponDefId,
                          @Param("type") String type);

    int deleteByTargetId(@Param("targetId") Long targetId, @Param("type") String type);

    List<Product> findBySearcher(@Param("pager") PageModel page, @Param("searcher") ProductSearcher searcher,
                                 @Param("defineId") Long defineId);

    int countBySearcher(@Param("searcher") ProductSearcher searcher, @Param("defineId") Long defineId);

    List<CouponDefRelation> findDesignerByDefId(@Param("couponDefId") Long couponDefId);

    List<CouponDef> findCouponsByDesigner(@Param("designerId") Long designerId);

    List<CouponDef> findCouponsByProduct(@Param("productId") Long productId);

    List<CouponDef> findCouponsByDesignerExclude(@Param("designerId") Long designerId);

}
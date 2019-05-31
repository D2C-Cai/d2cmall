package com.d2c.product.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSummaryMapper extends SuperMapper<ProductSummary> {

    ProductSummary findByProductId(Long productId);

    int updateViewsCount(@Param("views") Integer views, @Param("productId") Long productId);

    int updateCartsCount(@Param("carts") Integer carts, @Param("productId") Long productId);

    int updateOrdersCount(@Param("orders") Integer orders, @Param("productId") Long productId);

    int updateSalesCount(@Param("sales") Integer sales, @Param("productId") Long productId);

    int updateLikeCount(@Param("likes") Integer count, @Param("productId") Long productId);

    int updateConsultsCount(@Param("consults") Integer consults, @Param("productId") Long productId);

    int updateCommentsCount(@Param("comments") Integer comments, @Param("productId") Long productId);

    int updateRecentlySales(@Param("productId") Long productId, @Param("recentlySales") Integer recentlySales);

    List<Long> findAllRecentlySales();

    List<Long> findAllPartnerSales();

    int updatePartnerSales(@Param("productId") Long productId, @Param("sales") Integer sales);

}

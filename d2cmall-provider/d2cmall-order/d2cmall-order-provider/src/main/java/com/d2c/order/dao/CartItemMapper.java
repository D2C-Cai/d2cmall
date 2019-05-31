package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CartItem;
import com.d2c.order.query.CartItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemMapper extends SuperMapper<CartItem> {

    CartItem findById(Long id);

    CartItem findOneByMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    CartItem findBySkuAndMember(@Param("skuId") Long skuId, @Param("memberId") Long memberId);

    List<CartItem> findByMemberId(@Param("memberId") Long memberId);

    int countByMemberId(@Param("memberId") Long memberId);

    List<CartItem> findBySearcher(@Param("searcher") CartItemSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") CartItemSearcher searcher);

    int updateQuantity(@Param("productSkuId") Long productSkuId, @Param("memberId") Long memberId,
                       @Param("quantity") int quantity);

    int updatePrice(@Param("productSkuId") Long productSkuId, @Param("memberId") Long memberId,
                    @Param("originalPrice") BigDecimal originalPrice, @Param("price") BigDecimal price);

    int updateSku(@Param("id") Long id, @Param("productSkuName") String productSkuName,
                  @Param("productSkuSn") String productSkuSn, @Param("productSkuId") Long skuId, @Param("sp1") String sp1,
                  @Param("sp2") String sp2, @Param("sp3") String sp3, @Param("price") BigDecimal price);

    int delete(@Param("id") Long id, @Param("memberId") Long memberId);

    int deleteByMemberId(@Param("memberId") Long memberId);

    int deletes(@Param("ids") List<Long> cartItemIds, @Param("memberId") Long memberId);

}

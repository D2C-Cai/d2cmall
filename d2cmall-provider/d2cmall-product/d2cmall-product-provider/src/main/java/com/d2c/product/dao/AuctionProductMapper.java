package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.query.AuctionProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionProductMapper extends SuperMapper<AuctionProduct> {

    List<AuctionProduct> findBySearcher(@Param("searcher") AuctionProductSearcher searcher,
                                        @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") AuctionProductSearcher searcher);

    int doUp(Long id);

    int doDown(Long id);

    int updateCurrentPrice(@Param("id") Long id, @Param("offer") BigDecimal offer, @Param("marginId") Long marginId,
                           @Param("memberId") Long memberId);

    int updateOfferId(@Param("id") Long id, @Param("offerId") Long offerId);

    List<AuctionProduct> findExpiredAuctionProduct();

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}
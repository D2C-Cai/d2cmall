package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.query.AuctionMarginSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AuctionMarginMapper extends SuperMapper<AuctionMargin> {

    List<AuctionMargin> findBySearch(@Param("searcher") AuctionMarginSearcher searcher,
                                     @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") AuctionMarginSearcher searcher);

    AuctionMargin findByMarginSn(@Param("marginSn") String marginSn);

    AuctionMargin findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    AuctionMargin findByMarginSnAndMemberId(@Param("marginSn") String marginSn, @Param("memberId") Long memberId);

    AuctionMargin findByMemberIdAndAuctionId(@Param("memberId") Long memberId, @Param("auctionId") Long auctionId);

    int updateOffer(@Param("marginId") Long marginId, @Param("offer") BigDecimal offer);

    int updateAddress(@Param("marginId") Long marginId, @Param("addressId") Long addressId);

    int doSuccess(@Param("id") Long id, @Param("offer") BigDecimal offer);

    int doOut(@Param("auctionId") Long auctionId);

    int doBackMargin(@Param("id") Long id, @Param("refundType") String refundType, @Param("refundSn") String refundSn,
                     @Param("refunder") String refunder);

    int doBreachAuction(@Param("id") Long id);

    int doPaySuccess1(@Param("paymentType") Integer paymentType, @Param("paySn") String paySn,
                      @Param("payDate") Date payDate, @Param("paymentId") Long paymentId,
                      @Param("auctionMarginId") Long auctionMarginId);

    int doPaySuccess2(@Param("paymentType") Integer paymentType, @Param("paySn") String paySn,
                      @Param("payDate") Date payDate, @Param("paymentId") Long paymentId,
                      @Param("auctionMarginId") Long auctionMarginId);

    int doDelivery(@Param("id") Long id, @Param("deliveryCorpName") String deliveryCorpName,
                   @Param("deliverySn") String deliverySn);

    int doReceived(@Param("id") Long id);

    int getMaxStatus(@Param("auctionId") Long auctionId);

}
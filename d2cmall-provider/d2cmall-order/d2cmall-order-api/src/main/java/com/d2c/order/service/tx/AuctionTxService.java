package com.d2c.order.service.tx;

import com.d2c.member.support.OrderInfo;
import com.d2c.order.model.AuctionOffer;
import com.d2c.product.model.AuctionProduct;

import java.math.BigDecimal;

public interface AuctionTxService {

    /**
     * 添加一条出价记录，并且更新拍卖商品的当前价格
     *
     * @param product
     * @param memberInfoId
     * @param offer
     * @param marginId
     * @return
     */
    AuctionOffer insertOffer(AuctionProduct product, Long memberInfoId, BigDecimal offer, Long marginId);

    /**
     * 拍卖结束
     *
     * @param auctionProduct
     * @return
     */
    int doEndAuction(AuctionProduct auctionProduct);

    /**
     * 退款至钱包
     *
     * @param orderInfo
     * @param marginId
     * @param refundType
     * @param refundSn
     * @param refunder
     * @return
     */
    int doBackToWallet(OrderInfo orderInfo, Long marginId, String refundType, String refundSn, String refunder);

}

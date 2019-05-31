package com.d2c.order.dto;

import com.d2c.order.model.AuctionOffer;
import com.d2c.product.model.Product;

public class AuctionOfferDto extends AuctionOffer {

    private static final long serialVersionUID = 1L;
    /**
     * 拍卖商品
     */
    private Product auctionProduct;

    public Product getAuctionProduct() {
        return auctionProduct;
    }

    public void setAuctionProduct(Product auctionProduct) {
        this.auctionProduct = auctionProduct;
    }

}
